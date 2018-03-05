package com.badgersoft.datawarehouse.ukube.service;

import com.badgersoft.datawarehouse.ukube.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class HexFrameServiceImplTest {

    @Test
    public void ping() {
        HexFrameServiceImpl service = new HexFrameServiceImpl();
        assertTrue("Hello not received", "Hello".equals(service.ping()));
    }
}
