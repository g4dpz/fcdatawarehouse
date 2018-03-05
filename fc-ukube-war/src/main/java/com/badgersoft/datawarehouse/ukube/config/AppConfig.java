package com.badgersoft.datawarehouse.ukube.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans(value = { @ComponentScan("com.badgersoft.datawarehouse.ukube.controller"),
        @ComponentScan("com.badgersoft.datawarehouse.ukube.service") })
public class AppConfig {

}
