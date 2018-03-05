package com.badgersoft.datawarehouse.rawdata.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans(value = { @ComponentScan("com.badgersoft.datawarehouse.rawdata.controller"),
        @ComponentScan("com.badgersoft.datawarehouse.rawdata.service") })
public class AppConfig {

}
