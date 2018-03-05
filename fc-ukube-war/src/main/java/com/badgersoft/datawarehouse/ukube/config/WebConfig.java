package com.badgersoft.datawarehouse.ukube.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("com.badgersoft.datawarehouse.ukube.controller")
public class WebConfig implements WebMvcConfigurer {
}
