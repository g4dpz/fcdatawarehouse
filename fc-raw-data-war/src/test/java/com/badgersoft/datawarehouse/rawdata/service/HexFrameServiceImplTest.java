package com.badgersoft.datawarehouse.rawdata.service;

import com.badgersoft.datawarehouse.common.services.HexFrameService;
import com.badgersoft.datawarehouse.rawdata.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class})
public class HexFrameServiceImplTest {

    HexFrameService hexFrameService = new HexFrameServiceImpl();

    @Test
    public void process() {

    }
}
