package com.badgersoft.datawarehouse.nayif1.config;

import com.badgersoft.datawarehouse.nayif1.processor.*;
import com.badgersoft.datawarehouse.nayif1.service.RealtimeSummaryService;
import com.badgersoft.datawarehouse.nayif1.service.RealtimeSummaryServiceImpl;
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
    WodProcessor wodProcessor() { return new WodProcessorImpl(); }

    @Bean
    HighResProcessor highResProcessor() { return new HighResProcessorImpl(); }

    @Bean
    FitterMessageProcessor fitterMessageProcessor() { return new FitterMessageProcessorImpl(); }

    @Bean
    RealtimeProcessor realtimeProcessor() { return new RealtimeProcessorImpl(); }

    @Bean
    RealtimeSummaryService realtimeSummaryService() { return new RealtimeSummaryServiceImpl(); };

}
