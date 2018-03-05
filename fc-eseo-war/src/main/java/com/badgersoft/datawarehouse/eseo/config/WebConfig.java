package com.badgersoft.datawarehouse.eseo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("com.badgersoft.datawarehouse.eseo.controller")
public class WebConfig implements WebMvcConfigurer {
}
