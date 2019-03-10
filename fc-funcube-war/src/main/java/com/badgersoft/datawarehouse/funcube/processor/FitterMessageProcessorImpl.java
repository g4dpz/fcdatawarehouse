package com.badgersoft.datawarehouse.funcube.processor;

import com.badgersoft.datawarehouse.funcube.dao.FitterMessageDao;
import com.badgersoft.datawarehouse.funcube.dao.SatelliteStatusDao;
import com.badgersoft.datawarehouse.funcube.domain.FitterMessageEntity;
import com.badgersoft.datawarehouse.funcube.domain.SatelliteStatusEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by davidjohnson on 23/08/2016.
 */
public class FitterMessageProcessorImpl extends AbstractProcessor implements FitterMessageProcessor {

    private static Logger LOG = LoggerFactory.getLogger(FitterMessageProcessorImpl.class.getName());
    private static final SimpleTimeZone TZ = new SimpleTimeZone(0, "UTC");
    private static final String ONE = "1";

    @Autowired
    FitterMessageDao fitterMessageDao;
    @Autowired
    SatelliteStatusDao satelliteStatusDao;


    @Override
    public void process(Long sequenceNumber, Date satelliteTime, List<String> payloads) {

        long then = Calendar.getInstance().getTime().getTime();


        final Calendar cal = Calendar.getInstance(TZ);
        cal.add(Calendar.HOUR_OF_DAY, -24);

        Timestamp receivedDate = new Timestamp(cal.getTime().getTime());

        int frameType = 17;

        for (final String fitterFrame : payloads) {

            receivedDate = new Timestamp(satelliteTime.getTime());

            extractAndSaveFitter(frameType, fitterFrame, receivedDate);

            frameType++;
        }

        Iterable<SatelliteStatusEntity> satelliteStatuses = satelliteStatusDao.findAll();
        Iterator<SatelliteStatusEntity> iterator = satelliteStatuses.iterator();
        if (iterator.hasNext()) {
            final SatelliteStatusEntity satelliteStatus = iterator.next();
            final List<FitterMessageEntity> latestDebug = fitterMessageDao.getLatestDebug(new PageRequest(
                    0, 1));
            if (!latestDebug.isEmpty()) {
                final FitterMessageEntity fitter = latestDebug.get(0);
                final FitterDebug fitterDebug = new FitterDebug(
                        convertHexBytePairToBinary(fitter.getMessageText()));
                satelliteStatus.setEclipseModeForced(ONE.equals(fitterDebug.getEclipseForce()));
                satelliteStatus.setEclipseSwitch(ONE.equals(fitterDebug.getEclipseSwitch()));
            }

            satelliteStatus.setLastUpdated(new Timestamp(System.currentTimeMillis()));

            if (satelliteStatus.getLastFitterTime().getTime() < receivedDate.getTime()) {
                satelliteStatus.setLastFitterTime(receivedDate);
            }

            satelliteStatusDao.save(satelliteStatus);
        }

        long timeTaken = Calendar.getInstance().getTime().getTime() - then;
        LOG.debug(String.format("Processed fitter messages in %s mS", timeTaken));

    }


    private void extractAndSaveFitter(int frameType, final String messageHex, final Timestamp lastReceived) {

        StringBuffer sb = new StringBuffer();

        final String slot =  "FM" + (frameType - 16);

        // we may be processing a packet after a reset so the frame may contain 000's
        if ("00".equals(messageHex.substring(6, 8))) {
            return;
        }

        // we do not process DEBUG frames
        if (!"FF".equals(messageHex.substring(0, 2))) {

            for (int i = 0; i < messageHex.length() - 2; i += 2) {
                final int value = Integer.parseInt(messageHex.substring(i, i + 2), 16);
                if (value == 0) {
                    if (i == 0) {
                        break;
                    }
                    else {
                        final String messageText = sb.toString();
                        saveFitter(lastReceived, messageText, false, slot);
                        sb = new StringBuffer();
                    }
                }
                else {
                    sb.append((char)value);
                }
            }
        }
        else {
            final String messageText = messageHex.substring(0, messageHex.length());
            saveFitter(lastReceived, messageText, true, slot);
        }

    }

    private void saveFitter(final Timestamp lastReceived, final String messageText,
                            final Boolean isDebug, final String slot) {
        final List<FitterMessageEntity> fitterMessages = fitterMessageDao.findByMessageTextAndDebug(
                messageText,
                isDebug);

        FitterMessageEntity fitterMessage;

        if (fitterMessages.size() != 0) {
            fitterMessage = fitterMessages.get(0);
            fitterMessage.setLastReceived(lastReceived);
            fitterMessage.setSlot(slot);
        }
        else {
            fitterMessage = new FitterMessageEntity(messageText, lastReceived,
                    2L, isDebug, slot, lastReceived);
        }

        fitterMessageDao.save(fitterMessage);
    }
}
