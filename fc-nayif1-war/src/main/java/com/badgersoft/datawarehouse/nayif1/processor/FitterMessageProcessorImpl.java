package com.badgersoft.datawarehouse.nayif1.processor;

import com.badgersoft.datawarehouse.common.dto.HexFrameDTO;
import com.badgersoft.datawarehouse.nayif1.dao.FitterMessageDao;
import com.badgersoft.datawarehouse.nayif1.dao.SatelliteStatusDao;
import com.badgersoft.datawarehouse.nayif1.domain.FitterMessageEntity;
import com.badgersoft.datawarehouse.nayif1.domain.SatelliteStatusEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.SimpleTimeZone;

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
    public void process(HexFrameDTO[] fitterList) {

        long then = Calendar.getInstance().getTime().getTime();


        final Calendar cal = Calendar.getInstance(TZ);
        cal.add(Calendar.HOUR_OF_DAY, -24);

        Timestamp receivedDate = new Timestamp(cal.getTime().getTime());

        for (final HexFrameDTO fitterFrame : fitterList) {

            receivedDate = new Timestamp(fitterFrame.getCreatedDate().getTime());

            extractAndSaveFitter(fitterFrame, receivedDate);
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


    private void extractAndSaveFitter(final HexFrameDTO frame, final Timestamp lastReceived) {

        StringBuffer sb = new StringBuffer();

        final int frameType = frame.getFrameType().intValue();

        final String slot = getSlotFromFrameType(frameType);

        final String messageHex = frame.getHexString().substring(106, frame.getHexString().length());

        // we may be processing a packet after a reset so the frame may contain 000's
        if ("00".equals(messageHex.substring(6, 8))) {
            return;
        }

        // we do not process DEBUG frames
        if (!"FF".equals(messageHex.substring(6, 8))) {

            for (int i = 6; i < messageHex.length() - 2; i += 2) {
                final int value = Integer.parseInt(messageHex.substring(i, i + 2), 16);
                if (value == 0) {
                    if (i == 6) {
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
            final String messageText = messageHex.substring(8, messageHex.length());
            saveFitter(lastReceived, messageText, true, slot);
        }

    }

    private String getSlotFromFrameType(final int frameType) {

        String slot = "FM";

        switch (frameType) {
            case 13:
            case 14:
            case 15:
                slot += frameType - 12;
                break;
            case 17:
            case 18:
            case 19:
                slot += frameType - 13;
                break;
            case 21:
            case 22:
            case 23:
                slot += frameType - 14;
                break;
        }

        return slot;

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
            if (messageText.startsWith("ar-SA")) {
                int start = messageText.indexOf(" ");
                String arabicFitterHex = messageText.substring(start + 1, messageText.length());
                LOG.debug(String.format("Arabic hex: %s", arabicFitterHex));
                try {
                    String arabicFitterUnicode = new String(arabicFitterHex.getBytes(), "CP1250");
                    LOG.debug(String.format("Arabic unicode: %s", arabicFitterHex));
                } catch (UnsupportedEncodingException e) {
                    LOG.error("Failed to convert hex to UniCode");
                }
            }
            fitterMessage = new FitterMessageEntity(messageText, lastReceived,
                    11L, isDebug, slot, lastReceived);
        }

        fitterMessageDao.save(fitterMessage);
    }
}
