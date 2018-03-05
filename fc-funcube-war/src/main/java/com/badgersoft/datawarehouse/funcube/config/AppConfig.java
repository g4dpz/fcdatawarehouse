package com.badgersoft.datawarehouse.funcube.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans(value = { @ComponentScan("com.badgersoft.datawarehouse.funcube.controller"),
        @ComponentScan("com.badgersoft.datawarehouse.funcube.service") })
public class AppConfig {

}
