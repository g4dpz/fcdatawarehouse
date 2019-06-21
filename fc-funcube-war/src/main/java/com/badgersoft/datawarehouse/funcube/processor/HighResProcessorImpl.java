package com.badgersoft.datawarehouse.funcube.processor;

import com.badgersoft.datawarehouse.common.dto.HexFrameDTO;
import com.badgersoft.datawarehouse.funcube.dao.HighResolutionDAO;
import com.badgersoft.datawarehouse.funcube.dao.SatelliteStatusDao;
import com.badgersoft.datawarehouse.funcube.domain.HighResolutionEntity;
import com.badgersoft.datawarehouse.funcube.domain.SatelliteStatusEntity;
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
public class HighResProcessorImpl extends AbstractProcessor  implements HighResProcessor {

    private static Logger LOG = LoggerFactory.getLogger(HighResProcessorImpl.class.getName());

    @Autowired
    HighResolutionDAO highResolutionDAO;

    @Autowired
    SatelliteStatusDao satelliteStatusDao;

    @Override
    public void process(Long sequenceNumber, HexFrameDTO[] frames) {

        long then = Calendar.getInstance().getTime().getTime();

        final StringBuffer sb = new StringBuffer();

        for (int i = 0; i < 5; i++) {
            sb.append(StringUtils.right(frames[i].getHexString(), 400));
        }

        String binaryString = convertHexBytePairToBinary(sb.toString());

        Date refTime = frames[0].getSatelliteTime();

        Date satelliteTime = new Date(then);

        for (int i = 0; i < 60; i++) {
            satelliteTime = new Date(refTime.getTime() + (i * 1000));
            HighResolutionEntity highResolutionEntity
                    = new HighResolutionEntity(sequenceNumber, new Timestamp(satelliteTime.getTime()), binaryString.substring(i * 132, (i * 132) + 132));

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
