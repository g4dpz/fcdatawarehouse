package com.badgersoft.datawarehouse.funcube.processor;

import com.badgersoft.datawarehouse.common.dto.HexFrameDTO;
import com.badgersoft.datawarehouse.funcube.dao.RealtimeDAO;
import com.badgersoft.datawarehouse.funcube.dao.SatelliteStatusDao;
import com.badgersoft.datawarehouse.funcube.dao.WholeOrbitDataDAO;
import com.badgersoft.datawarehouse.funcube.domain.SatelliteStatusEntity;
import com.badgersoft.datawarehouse.funcube.domain.WholeOrbitDataEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

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
    public void process(Long sequenceNumber, HexFrameDTO[] frames) {

        long then = Calendar.getInstance().getTime().getTime();

        HexFrameDTO lastFrame = frames[11];

        Date lastSatelliteTime = lastFrame.getSatelliteTime();

        final Date firstFrameTime = new Date(
                lastSatelliteTime.getTime() - (96 * 60 * 1000));

        final StringBuffer sb = new StringBuffer();

        for (int i = 0; i < 12; i++) {
            sb.append(StringUtils.right(frames[i].getHexString(), 400));
        }

        String binaryString = convertHexBytePairToBinary(sb.toString());

        Date satelliteTime = new Date(then);

        for (long i = 0; i < 96; i++) {

            final long id = sequenceNumber * 2 + i;

            WholeOrbitDataEntity wholeOrbitDataEntity = wholeOrbitDataDAO.findById(id).get();

            if (wholeOrbitDataEntity == null) {
                int offset = (int) (i * 200);
                satelliteTime = new Date(firstFrameTime.getTime() + (i * 60L * 1000L));
                wholeOrbitDataEntity = new WholeOrbitDataEntity(id, sequenceNumber, satelliteTime, binaryString.substring(offset, offset + 200));
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
