package com.badgersoft.datawarehouse.jy1sat.messaging;

import com.badgersoft.datawarehouse.common.dto.HexFrameDTO;
import com.badgersoft.datawarehouse.jy1sat.dao.SatelliteStatusDao;
import com.badgersoft.datawarehouse.jy1sat.processor.FitterMessageProcessor;
import com.badgersoft.datawarehouse.jy1sat.processor.HighResProcessor;
import com.badgersoft.datawarehouse.jy1sat.processor.RealtimeProcessor;
import com.badgersoft.datawarehouse.jy1sat.processor.WodProcessor;
import com.badgersoft.datawarehouse.jy1sat.service.JmsMessageSender;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Receiver {

    @Autowired
    FitterMessageProcessor fitterMessageProcessor;

    @Autowired
    WodProcessor wodProcessor;

    @Autowired
    HighResProcessor highResProcessor;

    @Autowired
    RealtimeProcessor realtimeProcessor;

    @Autowired
    SatelliteStatusDao statusDao;

    private static Logger LOG = LoggerFactory.getLogger(Receiver.class.getName());

    private long sleep = 5000;

    @Autowired
    JmsMessageSender jmsMessageSender;

    private static String SATELLITE_NAME = "JY1Sat";

    private static String SATELLITE_ID = "19";

    private static String WAREHOUSE_PORT = "10080";

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @JmsListener(destination = "satellite_19_frame_available")
    @SendTo(value = "frame_processed")
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void receive(String message) {
        LOG.info("received message='{}'", message);



        // we do not process this immediately as we give several G/S the opportunity to 'score'
        // uploads
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
        }

        String[] messageElements = StringUtils.split(message, ",");
        int msgLength = messageElements.length;
        if (msgLength < 3 || msgLength > 4) {
            String errorMsg = String.format("Message for %s was incorrect: %s", SATELLITE_NAME, message);
            LOG.error(errorMsg);
        }

        // belt and braces check
        String satelliteId = messageElements[1];
        if (!satelliteId.equals(SATELLITE_ID)) {
            String errorMsg = String.format("Message was not for %s: %s", SATELLITE_NAME, message);
            LOG.error(errorMsg);
        }

        String frameTypeName = messageElements[0];
        String sequenceNumber = messageElements[2];

        if (frameTypeName.equals("rt")) {

            String frameTypeId = messageElements[3];

            LOG.debug(String.format("%s %s available for processing: %s", SATELLITE_NAME, frameTypeName, message));

            RestTemplate restTemplate = new RestTemplate();

            long then = Calendar.getInstance().getTime().getTime();


            final String url = "http://localhost:" + WAREHOUSE_PORT + "/api/data/frame/" +
                    satelliteId + "/" +
                    sequenceNumber + "/" +
                    frameTypeId;
            HexFrameDTO hexFrameDTO
                    = restTemplate.getForObject(
                    url, HexFrameDTO.class);

            try {

                long timeTaken = Calendar.getInstance().getTime().getTime() - then;
                LOG.debug(String.format("Received %s hexFrame %s in %d mS", SATELLITE_NAME, message, timeTaken));

                if (frameTypeName.equals("rt")) {
                    realtimeProcessor.process(hexFrameDTO);
                    if (hexFrameDTO.getFrameType() == 23) {
                        try {
                            processWOD(satelliteId, sequenceNumber, hexFrameDTO.getSatelliteTime(), "0,1,2,3,4,5,6,7,8,9,10,11");
                        }
                        catch (HttpClientErrorException h) {
                            LOG.error(String.format("Could not process WOD data for %s: %s", SATELLITE_NAME, h.getMessage()));
                        }
                        try {
                            processFitter(satelliteId, sequenceNumber, hexFrameDTO.getSatelliteTime(), "17,18,19,20,21,22,23");
                        }
                        catch (HttpClientErrorException h) {
                            LOG.error(String.format("Could not process Fitter data for %s: %s", SATELLITE_NAME, h.getMessage()));
                        }
                        try {
                            processHighRes(satelliteId, sequenceNumber, hexFrameDTO.getSatelliteTime(), "12,13,14,15,16");
                        }
                        catch (HttpClientErrorException h) {
                            LOG.error(String.format("Could not process High resolution data for %s: %s", SATELLITE_NAME, h.getMessage()));
                        }
                    }
                };

                jmsMessageSender.send("rt," + satelliteId + "," + sequenceNumber + "," + frameTypeId);
            }
            catch (Exception e) {
                String errorMsg = String.format("Could not process realtime data for %s: %s", SATELLITE_NAME, e.getMessage());
                LOG.error(errorMsg);
                jmsMessageSender.send(errorMsg);
            }

        }

        latch.countDown();
    }

    private void processFitter(String satelliteId, String sequenceNumber, Date satelliteTime, String frames) {

        LOG.info("Processing FUNcube Fitter messages for sequence number " + sequenceNumber);

        RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:10080/api/data/payload/" +
                satelliteId + "/" +
                sequenceNumber + "?" +
                "frames=" + frames;

        ResponseEntity<List<String>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>(){});
        List<String> payloads = response.getBody();

        LOG.info("Received " + ((payloads != null) ? payloads.size() : 0) + " FUNcube WOD payloads for sequence number " + sequenceNumber);

        fitterMessageProcessor.process(Long.valueOf(sequenceNumber), satelliteTime, payloads);

    }

    private void processWOD(String satelliteId, String sequenceNumber, Date satelliteTime, String frames) {

        LOG.info("Processing FUNcube WOD for sequence number " + sequenceNumber);

        RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:10080/api/data/payload/" +
                satelliteId + "/" +
                sequenceNumber + "?" +
                "frames=" + frames;

        ResponseEntity<List<String>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>(){});
        List<String> payloads = response.getBody();

        LOG.info("Received " + ((payloads != null) ? payloads.size() : 0) + " FUNcube WOD payloads for sequence number " + sequenceNumber);

        wodProcessor.process(Long.valueOf(sequenceNumber), satelliteTime, payloads);

    }

    private void processHighRes(String satelliteId, String sequenceNumber, Date satelliteTime, String frames) {

        LOG.info("Processing FUNcube High Resolution for sequence number " + sequenceNumber);

        RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:10080/api/data/payload/" +
                satelliteId + "/" +
                sequenceNumber + "?" +
                "frames=" + frames;

        ResponseEntity<List<String>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>(){});
        List<String> payloads = response.getBody();

        LOG.info("Received " + ((payloads != null) ? payloads.size() : 0) + " FUNcube High Resolution payloads for sequence number " + sequenceNumber);

        highResProcessor.process(Long.valueOf(sequenceNumber), satelliteTime, payloads);

    }
}
