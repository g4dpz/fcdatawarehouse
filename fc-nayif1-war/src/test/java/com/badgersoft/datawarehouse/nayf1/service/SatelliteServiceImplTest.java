package com.badgersoft.datawarehouse.nayf1.service;

import com.badgersoft.datawarehouse.nayif1.config.AppConfig;
import com.badgersoft.datawarehouse.nayif1.service.SatelliteServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class SatelliteServiceImplTest {

    @Test
    public void ping() {
        SatelliteServiceImpl service = new SatelliteServiceImpl();
        assertTrue("Hello not received", "Hello".equals(service.ping()));
    }
}
