package com.badgersoft.datawarehouse.nayif1.config;

import com.badgersoft.datawarehouse.common.services.SatelliteService;
import com.badgersoft.datawarehouse.nayif1.processor.*;
import com.badgersoft.datawarehouse.nayif1.service.SatelliteServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.badgersoft.datawarehouse.nayif1")
public class AppConfig {

    @Value("${satellite.queue}")
    String satelliteQueue;

    @Value("${broker.address}")
    String brokerAddress;

    @Bean
    SatelliteService nayif1RealtimeService() { return new SatelliteServiceImpl(); }

    @Bean
    WodProcessor wodProcessor() { return new WodProcessorImpl(); }

    @Bean
    HighResProcessor highResProcessor() { return new HighResProcessorImpl(); }

    @Bean
    FitterMessageProcessor fitterMessageProcessor() { return new FitterMessageProcessorImpl(); }

    @Bean
    RealtimeProcessor realtimeProcessor() { return new RealtimeProcessorImpl(); }

}
