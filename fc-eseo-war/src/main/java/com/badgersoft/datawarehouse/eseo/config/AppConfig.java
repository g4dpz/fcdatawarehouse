package com.badgersoft.datawarehouse.eseo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans(value = { @ComponentScan("com.badgersoft.datawarehouse.eseo.controller"),
        @ComponentScan("com.badgersoft.datawarehouse.eseo.service") })
public class AppConfig {

}
