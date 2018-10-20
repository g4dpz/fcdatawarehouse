package com.badgersoft.datawarehouse.jy1sat.service;

import com.badgersoft.datawarehouse.common.dto.HexFrameDTO;
import com.badgersoft.datawarehouse.common.services.AbstractSatelliteService;
import com.badgersoft.datawarehouse.common.services.SatelliteService;
import com.badgersoft.datawarehouse.jy1sat.dao.SatelliteStatusDao;
import com.badgersoft.datawarehouse.jy1sat.domain.SatelliteStatusEntity;
import com.badgersoft.datawarehouse.jy1sat.processor.FitterMessageProcessor;
import com.badgersoft.datawarehouse.jy1sat.processor.HighResProcessor;
import com.badgersoft.datawarehouse.jy1sat.processor.RealtimeProcessor;
import com.badgersoft.datawarehouse.jy1sat.processor.WodProcessor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;

/**
 * Created by davidjohnson on 14/08/2016.
 */
@Service
@Component(value = "jy1satSatelliteService")
public class SatelliteServiceImpl extends AbstractSatelliteService implements SatelliteService {

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

    @Override
    public String ping() {
        return null;
    }

    @Override
    @JmsListener(destination = "satellite_19_frame_available")
    @SendTo(value = "frame_processed")
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    public String handleFrameMessage(final String message) {

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
            return "Error: " + errorMsg;
        }

        // belt and braces check
        String satelliteId = messageElements[1];
        if (!satelliteId.equals(SATELLITE_ID)) {
            String errorMsg = String.format("Message was not for %s: %s", SATELLITE_NAME, message);
            LOG.error(errorMsg);
            return "Error: " + errorMsg;
        }

        String frameTypeName = messageElements[0];
        String sequenceNumber = messageElements[2];

        if (frameTypeName.equals("rt")) {

            String frameTypeId = messageElements[3];

            LOG.debug(String.format("%s %s available for processing: %s", SATELLITE_NAME, frameTypeName, message));

            RestTemplate restTemplate = new RestTemplate();

            long then = Calendar.getInstance().getTime().getTime();


            final String url = "http://localhost:8080/api/data/frame/" +
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
            }
            catch (Exception e) {
                String errorMsg = String.format("Could not process realtime data for %s: %s", SATELLITE_NAME, e.getMessage());
                LOG.error(errorMsg);
                return "Error: " + errorMsg;
            }

            return message;

        } else if (frameTypeName.equals("wod")) {

            return processFrames("wod", message, satelliteId, sequenceNumber);

        } else if (frameTypeName.equals("hp")) {

            return processFrames("highresolution", message, satelliteId, sequenceNumber);

        } else if (frameTypeName.equals("fitter")) {

            return processFrames("fitter", message, satelliteId, sequenceNumber);

        } else {
            String errorMessage = String.format("Error: unknown message type [%s] for %s", message, SATELLITE_NAME);
            LOG.error(errorMessage);
            return errorMessage;
        }
    }

    @Override
    @JmsListener(destination = "user_queue")
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public void handleUserMessage(final String message) {
        String[] messageElements = StringUtils.split(message, ",");
        int msgLength = messageElements.length;

        if (msgLength != 5) {
            String errorMsg = String.format("Message for %s was incorrect: %s", SATELLITE_NAME, message);
            LOG.error(errorMsg);
        }

        String frameTypeName = messageElements[0];
        String satelliteId = messageElements[1];
        Long sequenceNumber = Long.valueOf(messageElements[2]);
        Long frameType = Long.valueOf(messageElements[3]);
        String siteId = messageElements[4];

        // belt and braces check
        if (!(frameTypeName.equals("hfu") && satelliteId.equals(SATELLITE_ID)) ) {
            String errorMsg = String.format("User Message was not for %s: %s", SATELLITE_NAME, message);
            LOG.error(errorMsg);
        }
        else {
            LOG.debug("Adding user " + message);
            SatelliteStatusEntity satelliteStatusEntity = (SatelliteStatusEntity)(statusDao.findAll().iterator().next());
            String contributors = satelliteStatusEntity.getContributors();
            if (!contributors.contains(siteId)) {
                contributors += "," + siteId;
                satelliteStatusEntity.setContributors(contributors);
                statusDao.save(satelliteStatusEntity);
            }
        }
    }

    private String processFrames(String frameTypeName, String message, String satelliteId, String sequenceNumber) {
        LOG.debug(String.format("%s %s available for processing: %s", SATELLITE_NAME, frameTypeName, message));

        RestTemplate restTemplate = new RestTemplate();

        long then = Calendar.getInstance().getTime().getTime();

        HexFrameDTO[] frames
                = restTemplate.getForObject(
                "http://localhost:" + WAREHOUSE_PORT + "warehouse/api/data/" + frameTypeName + "/" +
                        satelliteId + "/" +
                        sequenceNumber, HexFrameDTO[].class);

        long timeTaken = Calendar.getInstance().getTime().getTime() - then;
        LOG.debug(String.format("Received %s %s %s in %s mS",
                SATELLITE_NAME, frameTypeName, message, timeTaken));

        if (frameTypeName.equals("wod")) {

            wodProcessor.process(Long.valueOf(sequenceNumber), frames);

        } else if (frameTypeName.equals("highresolution")) {

            highResProcessor.process(Long.valueOf(sequenceNumber), frames);

        } else if (frameTypeName.equals("fitter")) {

            fitterMessageProcessor.process(frames);
        }

        return message;
    }
}
