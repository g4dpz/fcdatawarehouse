package com.badgersoft.datawarehouse.eseo.processor;

import com.badgersoft.datawarehouse.common.dto.HexFrameDTO;
import com.badgersoft.datawarehouse.eseo.dao.*;
import com.badgersoft.datawarehouse.eseo.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by davidjohnson on 23/08/2016.
 */
public class RealtimeProcessorImpl extends AbstractProcessor implements RealtimeProcessor {

    private static AtomicLong channelValue = new AtomicLong(0L);

    public static final int MIN_MAX_VALUE_COUNT = 23;
    private static final long SEVEN_DAYS_MILLIS = 7 * 24 * 60 * 60 * 1000;
    private static Logger LOG = LoggerFactory.getLogger(RealtimeProcessorImpl.class.getName());

    @Autowired
    RealtimeDao realtimeDAO;

    @Autowired
    SatelliteStatusDao satelliteStatusDAO;

    @Autowired
    PayloadOneDao payloadOneDao;

    @Autowired
    PayloadTwoDao payloadTwoDao;

    @Autowired
    MinMaxDao minMaxDao;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = false)
    public void process(HexFrameDTO hexFrameDTO) throws Exception {

        long then = Calendar.getInstance().getTime().getTime();

        Long sequenceNumber = hexFrameDTO.getSequenceNumber();

        if (sequenceNumber >= 10000 && sequenceNumber <= 10010) {
            LOG.warn("Ignoring sequence number " + sequenceNumber);
            return;
        }

        Long frameType = hexFrameDTO.getFrameType();

        RealtimeEntity realtimeEntity = new RealtimeEntity();

        realtimeEntity.setSequenceNumber(sequenceNumber);
        realtimeEntity.setFrameType(hexFrameDTO.getFrameType());

        final String binaryString = convertHexBytePairToBinary(hexFrameDTO.getHexString());

        realtimeEntity.readBinary(binaryString);

        Date now = new Date(System.currentTimeMillis());
        realtimeEntity.setCreatedDate(hexFrameDTO.getCreatedDate());
        realtimeEntity.setSatelliteTime(now);
        realtimeEntity.setLatitude(hexFrameDTO.getLatitude());
        realtimeEntity.setLongitude(hexFrameDTO.getLongitude());

        // we need to update the satellite sequence number to the latest in the status record
        Iterable<SatelliteStatusEntity> satelliteStatuses = satelliteStatusDAO.findAll();
        Iterator<SatelliteStatusEntity> iterator = satelliteStatuses.iterator();
        if (iterator.hasNext()) {
            final SatelliteStatusEntity satelliteStatus = iterator.next();
            if (frameType %2 == 1) {
                // ODD Numbered Frames indicate Payload 1
                if (satelliteStatus.getSequenceNumber() == null || sequenceNumber > satelliteStatus.getSequenceNumber()) {
                    satelliteStatus.setSequenceNumber(sequenceNumber);
                    satelliteStatus.setFrameType(frameType);
                    satelliteStatus.setLastUpdated(new Timestamp(System.currentTimeMillis()));
                }
                else if (frameType > satelliteStatus.getFrameType()) {
                    satelliteStatus.setFrameType(frameType);
                    satelliteStatus.setLastUpdated(new Timestamp(System.currentTimeMillis()));
                }
            }
            else {
                // EVEN Numbered Frames indicate Payload 2
                if (satelliteStatus.getSequenceNumberTwo() == null || sequenceNumber > satelliteStatus.getSequenceNumberTwo()) {
                    satelliteStatus.setSequenceNumberTwo(sequenceNumber);
                    satelliteStatus.setFrameTypeTwo(frameType);
                    satelliteStatus.setLastUpdated(new Timestamp(System.currentTimeMillis()));
                }
                else if (frameType > satelliteStatus.getFrameTypeTwo()) {
                    satelliteStatus.setFrameTypeTwo(frameType);
                    satelliteStatus.setLastUpdated(new Timestamp(System.currentTimeMillis()));
                }
            }

            if (hexFrameDTO.getFrameType() %2 == 1) {
                PayloadOneEntity payloadOneEntity = new PayloadOneEntity();
                payloadOneEntity.readBinary(binaryString);
                payloadOneEntity.setSequenceNumber(sequenceNumber);
                payloadOneEntity.setFrameType(frameType);
                payloadOneEntity.setCreatedDate(hexFrameDTO.getCreatedDate());
                payloadOneEntity.setSatelliteTime(hexFrameDTO.getSatelliteTime());
                payloadOneDao.save(payloadOneEntity);
            }
            else {
                PayloadTwoEntity payloadTwoEntity = new PayloadTwoEntity();
                payloadTwoEntity.readBinary(binaryString);
                payloadTwoEntity.setSequenceNumber(sequenceNumber);
                payloadTwoEntity.setFrameType(frameType);
                payloadTwoEntity.setCreatedDate(hexFrameDTO.getCreatedDate());
                payloadTwoEntity.setSatelliteTime(hexFrameDTO.getSatelliteTime());
                payloadTwoDao.save(payloadTwoEntity);
            }

            // we may have just launched....
            if (satelliteStatus.getEpochSequenceNumber() == null && satelliteStatus.getEpochReferenceTime() == null) {
                satelliteStatus.setEpochSequenceNumber(sequenceNumber);
                satelliteStatus.setEpochReferenceTime(new Timestamp(System.currentTimeMillis()));
            }

            long satelliteTime
                    = satelliteStatus.getEpochReferenceTime().getTime()
                    + ((sequenceNumber - satelliteStatus.getEpochSequenceNumber()) * 5 * 24 * 1000)
                    + (frameType * 5 * 1000);

            realtimeEntity.setSatelliteTime(new Timestamp(satelliteTime));

            realtimeDAO.save(realtimeEntity);

            satelliteStatus.setLastRealtimeTime(new Timestamp(satelliteTime));
            satelliteStatus.setLastUpdated(new Timestamp(System.currentTimeMillis()));
            satelliteStatus.setContributors(StringUtils.join(hexFrameDTO.getContributors().toArray(), ","));
            satelliteStatus.setPacketCount(realtimeDAO.count());
            satelliteStatus.setEclipsed(realtimeEntity.getC27().equals("Yes"));

            satelliteStatusDAO.save(satelliteStatus);
            updateMinMax(realtimeEntity);
        }

        long timeTaken = Calendar.getInstance().getTime().getTime() - then;
        LOG.debug(String.format("Processed realtime messages in %s mS", timeTaken));

    }

    private void updateMinMax(RealtimeEntity realtimeEntity) {
        List<MinMaxEntity> minMaxEntities = minMaxDao.getAllEnabled();
        if (minMaxEntities.size() == MIN_MAX_VALUE_COUNT) {
            long refDate = minMaxEntities.get(0).getRefDate().getTime();
            long now = System.currentTimeMillis();
            boolean refresh = (now - refDate) > SEVEN_DAYS_MILLIS;
            for (MinMaxEntity minMax : minMaxEntities) {
                Long channel = minMax.getChannel();
                Double value = getChannelValue(channel, realtimeEntity);
                if (refresh) {
                    minMax.setEnabled(false);
                    minMaxDao.save(minMax);
                    MinMaxEntity newMinMax = new MinMaxEntity(channel, value, value, new Date(now), true);
                    minMaxDao.save(newMinMax);
                }
                else if (value > minMax.getMaximum()) {
                        minMax.setMaximum(value);
                        minMaxDao.save(minMax);
                }
                else if (value < minMax.getMinimum()) {
                        minMax.setMinimum(value);
                        minMaxDao.save(minMax);
                }
            }
        }
        else {
            LOG.error("Did not find " + MIN_MAX_VALUE_COUNT + " minmax values");
        }
    }

    private Double getChannelValue(Long channel, RealtimeEntity realtimeEntity) {
        switch (channel.intValue()) {
            case 1:
                return Double.valueOf(realtimeEntity.getC1());
            case 2:
                return Double.valueOf(realtimeEntity.getC2());
            case 3:
                return Double.valueOf(realtimeEntity.getC3());
            case 4:
                return Double.valueOf(realtimeEntity.getC4());
            case 5:
                return Double.valueOf(realtimeEntity.getC5());
            case 6:
                return Double.valueOf(realtimeEntity.getC6());
            case 7:
                return Double.valueOf(realtimeEntity.getC7());
            case 8:
                return Double.valueOf(realtimeEntity.getC8());
            case 9:
                return Double.valueOf(realtimeEntity.getC9());
            case 10:
                return Double.valueOf(realtimeEntity.getC10());
            case 11:
                return Double.valueOf(realtimeEntity.getC11());
            case 12:
                return Double.valueOf(realtimeEntity.getC12());
            case 13:
                return Double.valueOf(realtimeEntity.getC13());
            case 14:
                return Double.valueOf(realtimeEntity.getC14());
            case 15:
                return Double.valueOf(realtimeEntity.getC15());
            case 16:
                return Double.valueOf(realtimeEntity.getC16());
            case 17:
                return Double.valueOf(realtimeEntity.getC17());
            case 18:
                return Double.valueOf(realtimeEntity.getC18());
            case 19:
                return Double.valueOf(realtimeEntity.getC19());
            case 20:
                return Double.valueOf(realtimeEntity.getC20());
            case 21:
                return Double.valueOf(realtimeEntity.getC20());
            case 22:
                return Double.valueOf(realtimeEntity.getC22());
            case 23:
                return Double.valueOf(realtimeEntity.getC23());
            default:
                return 0.0;
        }
    }
}
