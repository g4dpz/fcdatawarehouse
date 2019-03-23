package com.badgersoft.datawarehouse.funcube.processor;

import com.badgersoft.datawarehouse.funcube.dao.HighResolutionDAO;
import com.badgersoft.datawarehouse.funcube.dao.SatelliteStatusDao;
import com.badgersoft.datawarehouse.funcube.domain.HighResolutionEntity;
import com.badgersoft.datawarehouse.funcube.domain.SatelliteStatusEntity;
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

        long then = Calendar.getInstance().getTime().getTime();

        final StringBuffer sb = new StringBuffer();

        for (int i = 0; i < 3; i++) {
            sb.append(frames.get(i));
        }

        String binaryString = convertHexBytePairToBinary(sb.toString());

        Date refTime = new Date();

        for (int i = 0; i < 60; i++) {
            refTime = new Date(satelliteTime.getTime() + (i * 1000));
            HighResolutionEntity highResolutionEntity
                    = new HighResolutionEntity(sequenceNumber, new Timestamp(refTime.getTime()), binaryString.substring(i * 80, (i * 80) + 80));

            try {
                highResolutionDAO.save(highResolutionEntity);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        SatelliteStatusEntity statusEntity = satelliteStatusDao.findAll().iterator().next();
        statusEntity.setLastHighresTime(new Timestamp(refTime.getTime()));
        satelliteStatusDao.save(statusEntity);


    }
}
