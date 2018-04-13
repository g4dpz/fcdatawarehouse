package com.badgersoft.datawarehouse.funcube.service;

import com.badgersoft.datawarehouse.funcube.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class})
public class SatelliteServiceImplTest {

    @Test
    public void ping() {
        SatelliteServiceImpl service = new SatelliteServiceImpl();
        assertTrue("Hello not received", "Hello".equals(service.ping()));
    }
}
