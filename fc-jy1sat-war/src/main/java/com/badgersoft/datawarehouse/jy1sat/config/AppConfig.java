package com.badgersoft.datawarehouse.jy1sat.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans(value = { @ComponentScan("com.badgersoft.datawarehouse.jy1sat.controller"),
        @ComponentScan("com.badgersoft.datawarehouse.jy1sat.service") })
public class AppConfig {

}
