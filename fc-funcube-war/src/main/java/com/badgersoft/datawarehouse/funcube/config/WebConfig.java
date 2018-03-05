package com.badgersoft.datawarehouse.funcube.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.badgersoft.datawarehouse.funcube.controller" })
public class WebConfig implements WebMvcConfigurer {
}
