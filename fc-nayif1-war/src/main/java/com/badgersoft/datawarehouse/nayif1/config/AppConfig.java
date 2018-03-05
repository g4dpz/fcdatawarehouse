package com.badgersoft.datawarehouse.nayif1.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans(value = { @ComponentScan("com.badgersoft.datawarehouse.nayif1.controller"),
        @ComponentScan("com.badgersoft.datawarehouse.nayif1.service") })
public class AppConfig {

}
