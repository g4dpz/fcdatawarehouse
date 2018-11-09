package com.badgersoft.datawarehouse.eseo.processor;

import com.badgersoft.datawarehouse.common.dto.HexFrameDTO;
import com.badgersoft.datawarehouse.eseo.dao.RealtimeOneDao;
import com.badgersoft.datawarehouse.eseo.dao.SatelliteStatusDao;
import com.badgersoft.datawarehouse.eseo.domain.RealtimeOneEntity;
import com.badgersoft.datawarehouse.eseo.domain.SatelliteStatusEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by davidjohnson on 23/08/2016.
 */
public class RealtimeOneProcessorImpl extends AbstractProcessor implements RealtimeProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RealtimeOneProcessorImpl.class);

    private static AtomicLong channelValue = new AtomicLong(0L);

    @Autowired
    RealtimeOneDao realtimeOneDAO;

    @Autowired
    SatelliteStatusDao satelliteStatusDAO;

    @Override
    public void process(HexFrameDTO hexFrameDTO) throws Exception {

        long then = Calendar.getInstance().getTime().getTime();

        Long sequenceNumber = hexFrameDTO.getSequenceNumber();
        Long frameType = hexFrameDTO.getFrameType();

        RealtimeOneEntity realtimeEntity = new RealtimeOneEntity();

        realtimeEntity.setSequenceNumber(sequenceNumber);
        realtimeEntity.setFrameType(hexFrameDTO.getFrameType());

        final String binaryString = convertHexBytePairToBinary(hexFrameDTO.getHexString()
                .substring(4, hexFrameDTO.getHexString().length()));

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
            if (satelliteStatus.getSequenceNumberOne() == null || sequenceNumber > satelliteStatus.getSequenceNumberOne()) {
                satelliteStatus.setSequenceNumberOne(sequenceNumber);

                if (frameType % 2 == 0) {
                    satelliteStatus.setLastUpdated(new Timestamp(System.currentTimeMillis()));
                }
                else {
                    satelliteStatus.setLastUpdatedRealtimeTwo(new Timestamp(System.currentTimeMillis()));
                }
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

            satelliteStatusDAO.save(satelliteStatus);
        }

        realtimeOneDAO.save(realtimeEntity);

        long timeTaken = Calendar.getInstance().getTime().getTime() - then;
        LOGGER.debug(String.format("Processed realtime messages in %s mS", timeTaken));

    }
}
