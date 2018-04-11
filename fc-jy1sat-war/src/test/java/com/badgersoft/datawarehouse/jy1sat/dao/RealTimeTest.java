package com.badgersoft.datawarehouse.jy1sat.dao;

import com.badgersoft.datawarehouse.jy1sat.config.TestJpaConfig;
import com.badgersoft.datawarehouse.jy1sat.domain.RealtimeEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestJpaConfig.class},
        loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
@Transactional()
public class RealTimeTest {

    @Autowired
    RealtimeDAO realtimeDAO;

    @Test
    public void create() {

        RealtimeEntity realtimeEntity1 = new RealtimeEntity();
        realtimeEntity1.setSequenceNumber(1L);
        realtimeEntity1.setFrameType(2L);
        realtimeEntity1.setCreatedDate(new Date(System.currentTimeMillis()));

        realtimeDAO.save(realtimeEntity1);

        RealtimeEntity realtimeEntity2 = new RealtimeEntity();
        realtimeEntity2.setSequenceNumber(3L);
        realtimeEntity2.setFrameType(4L);
        realtimeEntity2.setCreatedDate(new Date(System.currentTimeMillis()));

        realtimeDAO.save(realtimeEntity2);

        final RealtimeEntity realtimeEntity3 = realtimeDAO.findBySequenceNumberAndFrameType(3L, 4L);

        assertNotNull(realtimeEntity3);

        final RealtimeEntity realtimeEntity4 = realtimeDAO.findFirst1ByOrderByIdDesc();

        assertTrue(3L == realtimeEntity4.getSequenceNumber());


    }
}
