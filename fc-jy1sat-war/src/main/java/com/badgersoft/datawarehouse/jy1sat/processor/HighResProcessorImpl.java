package com.badgersoft.datawarehouse.jy1sat.processor;

import com.badgersoft.datawarehouse.jy1sat.dao.HighResolutionDAO;
import com.badgersoft.datawarehouse.jy1sat.dao.SatelliteStatusDao;
import com.badgersoft.datawarehouse.jy1sat.domain.HighResolutionEntity;
import com.badgersoft.datawarehouse.jy1sat.domain.SatelliteStatusEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by davidjohnson on 23/08/2016.
 */
public class HighResProcessorImpl extends AbstractProcessor  implements HighResProcessor {

    private static Logger LOG = LoggerFactory.getLogger(HighResProcessorImpl.class.getName());

    @Autowired
    HighResolutionDAO highResolutionDAO;

    @Autowired
    SatelliteStatusDao satelliteStatusDao;

    @Override
    public void process(Long sequenceNumber, Date satelliteTime, List<String> frames) {

        final StringBuffer sb = new StringBuffer();
        long then = Calendar.getInstance().getTime().getTime();

        for (int i = 0; i < 5; i++) {
            sb.append(StringUtils.right(frames.get(i), 400));
        }

        String binaryString = convertHexBytePairToBinary(sb.toString());

        Date refTime = new Date(satelliteTime.getTime() - 59000);

        Date frameTime;

        for (int i = 0; i < 60; i++) {
            frameTime = new Date(refTime.getTime() + (i * 1000));
            HighResolutionEntity highResolutionEntity
                    = new HighResolutionEntity(sequenceNumber, new Timestamp(frameTime.getTime()), binaryString.substring(i * 132, (i * 132) + 132));

            try {
                highResolutionDAO.save(highResolutionEntity);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        SatelliteStatusEntity statusEntity = satelliteStatusDao.findAll().iterator().next();
        statusEntity.setLastHighresTime(new Timestamp(satelliteTime.getTime()));
        satelliteStatusDao.save(statusEntity);

        long timeTaken = Calendar.getInstance().getTime().getTime() - then;
        LOG.debug(String.format("Processed highRes messages in %s mS", timeTaken));

    }
}
