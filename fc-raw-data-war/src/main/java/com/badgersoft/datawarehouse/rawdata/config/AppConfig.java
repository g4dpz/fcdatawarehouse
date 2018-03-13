package com.badgersoft.datawarehouse.rawdata.config;

import com.badgersoft.datawarehouse.common.services.HexFrameService;
import com.badgersoft.datawarehouse.common.utils.UTCClock;
import com.badgersoft.datawarehouse.rawdata.service.HexFrameServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans(value = { @ComponentScan("com.badgersoft.datawarehouse.rawdata.controller"),
        @ComponentScan("com.badgersoft.datawarehouse.rawdata.service") })
public class AppConfig {

    @Bean
    HexFrameService hexFrameService() { return new HexFrameServiceImpl(); }

    @Bean
    UTCClock clock() { return new UTCClock(); }

    @Bean
    EnvConfig envConfig() { return new EnvConfigImpl(); }

}
