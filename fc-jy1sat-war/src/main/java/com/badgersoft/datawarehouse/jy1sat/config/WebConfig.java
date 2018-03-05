package com.badgersoft.datawarehouse.jy1sat.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("com.badgersoft.datawarehouse.jy1sat.controller")
public class WebConfig implements WebMvcConfigurer {
}
