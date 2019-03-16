package com.badgersoft.datawarehouse.funcube.processor;

import com.badgersoft.datawarehouse.funcube.dao.RealtimeDAO;
import com.badgersoft.datawarehouse.funcube.dao.SatelliteStatusDao;
import com.badgersoft.datawarehouse.funcube.dao.WholeOrbitDataDAO;
import com.badgersoft.datawarehouse.funcube.domain.SatelliteStatusEntity;
import com.badgersoft.datawarehouse.funcube.domain.WholeOrbitDataEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by davidjohnson on 23/08/2016.
 */
public class WodProcessorImpl extends AbstractProcessor implements WodProcessor {

    private static Logger LOG = LoggerFactory.getLogger(WodProcessorImpl.class.getName());

    @Autowired
    RealtimeDAO realtimeDAO;

    @Autowired
    WholeOrbitDataDAO wholeOrbitDataDAO;

    @Autowired
    SatelliteStatusDao satelliteStatusDao;

    @Override
    public void process(Long sequenceNumber, Date satelliteTime, List<String> frames) {

        long then = Calendar.getInstance().getTime().getTime();

        final Date firstFrameTime = new Date(
                satelliteTime.getTime() - (104 * 60 * 1000));

        final StringBuffer sb = new StringBuffer();

        for (String frame : frames) {
            sb.append(frame);
        }

        String binaryString = convertHexBytePairToBinary(sb.toString());

        for (long i = 0; i < 104; i++) {

            final long id = sequenceNumber * 2 + i;

            final Optional<WholeOrbitDataEntity> dataEntity = wholeOrbitDataDAO.findById(id);

            if (!dataEntity.isPresent()) {
                int offset = (int) (i * 184);
                satelliteTime = new Date(firstFrameTime.getTime() + (i * 60L * 1000L));
                WholeOrbitDataEntity wholeOrbitDataEntity = new WholeOrbitDataEntity(id, sequenceNumber, satelliteTime, binaryString.substring(offset, offset + 184));
                wholeOrbitDataDAO.save(wholeOrbitDataEntity);

                SatelliteStatusEntity statusEntity = satelliteStatusDao.findAll().iterator().next();

                if (statusEntity.getLastWodTime().getTime() < satelliteTime.getTime()) {
                    statusEntity.setLastWodTime(new Timestamp(satelliteTime.getTime()));
                }

                statusEntity.setLastUpdated(new Timestamp(System.currentTimeMillis()));
                satelliteStatusDao.save(statusEntity);
            }
        }

        long timeTaken = Calendar.getInstance().getTime().getTime() - then;
        LOG.debug(String.format("Processed wod messages in %s mS", timeTaken));

    }
}
