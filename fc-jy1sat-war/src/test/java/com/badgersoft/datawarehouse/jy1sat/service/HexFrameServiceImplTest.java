package com.badgersoft.datawarehouse.jy1sat.service;

import com.badgersoft.datawarehouse.jy1sat.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class HexFrameServiceImplTest {

    @Test
    public void ping() {
        HexFrameServiceImpl service = new HexFrameServiceImpl();
        assertTrue("Hello not received", "Hello".equals(service.ping()));
    }
}
