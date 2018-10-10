package com.badgersoft.datawarehouse.jy1sat.messaging;

import com.badgersoft.datawarehouse.common.dto.HexFrameDTO;
import com.badgersoft.datawarehouse.jy1sat.dao.SatelliteStatusDao;
import com.badgersoft.datawarehouse.jy1sat.processor.FitterMessageProcessor;
import com.badgersoft.datawarehouse.jy1sat.processor.HighResProcessor;
import com.badgersoft.datawarehouse.jy1sat.processor.RealtimeProcessor;
import com.badgersoft.datawarehouse.jy1sat.processor.WodProcessor;
import com.badgersoft.datawarehouse.jy1sat.service.JmsMessageSender;
import com.badgersoft.datawarehouse.jy1sat.service.SatelliteServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
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

    private static Logger LOG = LoggerFactory.getLogger(SatelliteServiceImpl.class.getName());

    @Autowired
    JmsMessageSender jmsMessageSender;

    private static String SATELLITE_NAME = "JY1Sat";

    private static String SATELLITE_ID = "19";

    private static String WAREHOUSE_PORT = "8080";

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @JmsListener(destination = "satellite_19_frame_available")
    @SendTo(value = "frame_processed")
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    public void receive(String message) {
        LOGGER.info("received message='{}'", message);



        // we do not process this immediately as we give several G/S the opportunity to 'score'
        // uploads
        try {
            Thread.sleep(5000);
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
}
