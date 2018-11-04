package com.badgersoft.datawarehouse.funcube.config;

import com.badgersoft.datawarehouse.common.services.SatelliteService;
import com.badgersoft.datawarehouse.funcube.processor.*;
import com.badgersoft.datawarehouse.funcube.service.SatelliteServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.badgersoft.datawarehouse.funcube")
public class AppConfig {

    @Value("${satellite.queue}")
    String satelliteQueue;

    @Value("${broker.address}")
    String brokerAddress;

    @Bean
    SatelliteService funcubeRealtimeService() { return new SatelliteServiceImpl(); }

    @Bean
    WodProcessor wodProcessor() { return new WodProcessorImpl(); }

    @Bean
    HighResProcessor highResProcessor() { return new HighResProcessorImpl(); }

    @Bean
    FitterMessageProcessor fitterMessageProcessor() { return new FitterMessageProcessorImpl(); }

    @Bean
    RealtimeProcessor realtimeProcessor() { return new RealtimeProcessorImpl(); }

}
