package com.badgersoft.datawarehouse.eseo.processor;

import com.badgersoft.datawarehouse.common.dto.HexFrameDTO;
import com.badgersoft.datawarehouse.eseo.dao.PayloadOneDao;
import com.badgersoft.datawarehouse.eseo.dao.PayloadTwoDao;
import com.badgersoft.datawarehouse.eseo.dao.RealtimeDao;
import com.badgersoft.datawarehouse.eseo.dao.SatelliteStatusDao;
import com.badgersoft.datawarehouse.eseo.domain.PayloadOneEntity;
import com.badgersoft.datawarehouse.eseo.domain.PayloadTwoEntity;
import com.badgersoft.datawarehouse.eseo.domain.RealtimeEntity;
import com.badgersoft.datawarehouse.eseo.domain.SatelliteStatusEntity;
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
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by davidjohnson on 23/08/2016.
 */
public class RealtimeProcessorImpl extends AbstractProcessor implements RealtimeProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RealtimeProcessorImpl.class);

    private static AtomicLong channelValue = new AtomicLong(0L);

    @Autowired
    RealtimeDao realtimeDAO;

    @Autowired
    SatelliteStatusDao satelliteStatusDAO;

    @Autowired
    PayloadOneDao payloadOneDao;

    @Autowired
    PayloadTwoDao payloadTwoDao;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = false)
    public void process(HexFrameDTO hexFrameDTO) throws Exception {

        long then = Calendar.getInstance().getTime().getTime();

        Long sequenceNumber = hexFrameDTO.getSequenceNumber();
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
        }

        long timeTaken = Calendar.getInstance().getTime().getTime() - then;
        LOGGER.debug(String.format("Processed realtime messages in %s mS", timeTaken));

    }
}
