package com.badgersoft.datawarehouse.nayif1.processor;

import com.badgersoft.datawarehouse.common.dto.HexFrameDTO;
import com.badgersoft.datawarehouse.nayif1.dao.MinMaxDao;
import com.badgersoft.datawarehouse.nayif1.dao.RealtimeDAO;
import com.badgersoft.datawarehouse.nayif1.dao.SatelliteStatusDao;
import com.badgersoft.datawarehouse.nayif1.domain.MinMaxEntity;
import com.badgersoft.datawarehouse.nayif1.domain.RealtimeEntity;
import com.badgersoft.datawarehouse.nayif1.domain.SatelliteStatusEntity;
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

    public static final int MIN_MAX_VALUE_COUNT = 43;
    private static final long SEVEN_DAYS_MILLIS = 7 * 24 * 60 * 60 * 1000;
    private static Logger LOG = LoggerFactory.getLogger(RealtimeProcessorImpl.class.getName());
    private static AtomicLong channelValue = new AtomicLong(0L);

    @Autowired
    RealtimeDAO realtimeDAO;

    @Autowired
    SatelliteStatusDao satelliteStatusDao;

    @Autowired
    MinMaxDao minMaxDao;

//    @Autowired
//    MailService mailService;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = false)
    public void process(HexFrameDTO hexFrameDTO) {

        long then = Calendar.getInstance().getTime().getTime();

        RealtimeEntity realtimeEntity = new RealtimeEntity();
        Long sequenceNumber = hexFrameDTO.getSequenceNumber();
        Long frameType = hexFrameDTO.getFrameType();
        realtimeEntity.setSequenceNumber(sequenceNumber);
        realtimeEntity.setFrameType(frameType);

        final String binaryString = convertHexBytePairToBinary(hexFrameDTO.getHexString()
                .substring(4, hexFrameDTO.getHexString().length()));

        realtimeEntity.readBinary(binaryString);

        Date now = new Date(System.currentTimeMillis());
        realtimeEntity.setCreatedDate(hexFrameDTO.getCreatedDate());
        realtimeEntity.setLatitude(hexFrameDTO.getLatitude());
        realtimeEntity.setLongitude(hexFrameDTO.getLongitude());

        // we need to update the satellite sequence number to the latest in the status record
        Iterable<SatelliteStatusEntity> satelliteStatuses = satelliteStatusDao.findAll();
        Iterator<SatelliteStatusEntity> iterator = satelliteStatuses.iterator();
        if (iterator.hasNext()) {
            final SatelliteStatusEntity satelliteStatus = iterator.next();
            if (satelliteStatus.getSequenceNumber() == null ||
                    (sequenceNumber.longValue() > satelliteStatus.getSequenceNumber().longValue()) ||
                    ((sequenceNumber.longValue() == satelliteStatus.getSequenceNumber().longValue()) && frameType.longValue() > satelliteStatus.getFrameType().longValue()))
            {
                satelliteStatus.setSequenceNumber(sequenceNumber);
                satelliteStatus.setFrameType(frameType);

                // we may have just launched....
                if (satelliteStatus.getEpochSequenceNumber() == null && satelliteStatus.getEpochReferenceTime() == null) {
                    satelliteStatus.setEpochSequenceNumber(sequenceNumber);
                    satelliteStatus.setEpochReferenceTime(new Date(System.currentTimeMillis()));
                }

                long satelliteTime
                        = satelliteStatus.getEpochReferenceTime().getTime()
                        + ((sequenceNumber - satelliteStatus.getEpochSequenceNumber()) * 5 * 24 * 1000)
                        + (frameType * 5 * 1000);

                realtimeEntity.setSatelliteTime(new Timestamp(satelliteTime));


                satelliteStatus.setLastRealtimeTime(new Timestamp(satelliteTime));
                satelliteStatus.setLastUpdated(new Timestamp(System.currentTimeMillis()));
                satelliteStatus.setContributors(StringUtils.join(hexFrameDTO.getContributors().toArray(), ","));
                satelliteStatus.setPacketCount(realtimeDAO.count());
                satelliteStatus.setEclipsed(realtimeEntity.getC58());

                LOG.debug("Saving realtime entity");
                realtimeDAO.save(realtimeEntity);
                LOG.debug("Saving satellite status");
                satelliteStatusDao.save(satelliteStatus);
                updateMinMax(realtimeEntity);
            }
        }

        long timeTaken = Calendar.getInstance().getTime().getTime() - then;
        LOG.debug(String.format("Processed realtime messages in %s mS", timeTaken));



    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = false)
    public void hasBeenSeen() {
        Iterable<SatelliteStatusEntity> statuses = satelliteStatusDao.findAll();
        SatelliteStatusEntity status = statuses.iterator().next();
        long now = System.currentTimeMillis();
        Date lastUpdated = status.getLastUpdated();
        long sinceUpdated = now - lastUpdated.getTime();
        long sinceNotified = now - status.getLastNoShowNotification().getTime();

        if (sinceUpdated > 120000) {
            if (sinceNotified > sinceUpdated) {
                // send message
//                Map<String, Object> emailTags = new HashedMap();
//                emailTags.put("satelliteName", "Nayif-1");
//                emailTags.put("lastUpdated", lastUpdated.toString());
//                mailService.sendUsingTemplate("operations@funcube.org.uk", emailTags, "noshow");
//
//                status.setLastNoShowNotification(new Timestamp(now));
//                satelliteStatusDao.save(status);
            }
        }
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
                    if (channel != 22 || (channel == 22 && realtimeEntity.getC20() == 1)) {
                        minMax.setMaximum(value);
                        minMaxDao.save(minMax);
                    }
                }
                else if (value < minMax.getMinimum()) {
                    if (channel != 22 || (channel == 22 && realtimeEntity.getC20() == 1)) {
                        minMax.setMinimum(value);
                        minMaxDao.save(minMax);
                    }
                }
            }
        }
        else {
            LOG.error("Did not find " + MIN_MAX_VALUE_COUNT + " minmax values");
        }
    }

    private Double getChannelValue(Long channel, RealtimeEntity realtimeEntity) {
        switch(channel.intValue()) {
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
                /* channel 21 is a boolean */
            case 22:
                return Double.valueOf(realtimeEntity.getC22());
            case 23:
                return Double.valueOf(realtimeEntity.getC23());
            case 24:
                return Double.valueOf(realtimeEntity.getC24());
            case 25:
                return Double.valueOf(realtimeEntity.getC25());
            case 26:
                return Double.valueOf(realtimeEntity.getC26());
            case 27:
                return Double.valueOf(realtimeEntity.getC27());
            case 28:
                return Double.valueOf(realtimeEntity.getC28());
            case 29:
                return Double.valueOf(realtimeEntity.getC29());
            case 30:
                return Double.valueOf(realtimeEntity.getC30());
            case 31:
                return Double.valueOf(realtimeEntity.getC31());
            case 32:
                return Double.valueOf(realtimeEntity.getC32());
            case 33:
                return Double.valueOf(realtimeEntity.getC33());
            case 34:
                return Double.valueOf(realtimeEntity.getC34());
            case 35:
                return Double.valueOf(realtimeEntity.getC35());
            case 36:
                return Double.valueOf(realtimeEntity.getC36());
            case 37:
                return Double.valueOf(realtimeEntity.getC37());
            case 38:
                return Double.valueOf(realtimeEntity.getC38());
            case 39:
                return Double.valueOf(realtimeEntity.getC39());
            case 40:
                return Double.valueOf(realtimeEntity.getC40());
            case 41:
                return Double.valueOf(realtimeEntity.getC41());
            case 42:
                return Double.valueOf(realtimeEntity.getC42());
            case 43:
                return Double.valueOf(realtimeEntity.getC43());
            default:
                return 0.0;
        }
    }
}
