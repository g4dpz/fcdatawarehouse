package com.badgersoft.datawarehouse.rawdata.config;

import com.badgersoft.datawarehouse.common.utils.UTCClock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans(value = { @ComponentScan("com.badgersoft.datawarehouse.rawdata.controller"),
        @ComponentScan("com.badgersoft.datawarehouse.rawdata.service") })
public class AppConfig {

    @Bean
    UTCClock clock() { return new UTCClock(); }
}
