package com.badgersoft.datawarehouse.eseo.config;

import com.badgersoft.datawarehouse.eseo.processor.RealtimeProcessorImpl;
import com.badgersoft.datawarehouse.eseo.processor.RealtimeProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.badgersoft.datawarehouse.eseo")
public class AppConfig {

    @Value("${satellite.queue}")
    String satelliteQueue;

    @Value("${broker.address}")
    String brokerAddress;

//    @Bean
//    WodProcessor wodProcessor() { return new WodProcessorImpl(); }
//
//    @Bean
//    FitterMessageProcessor fitterMessageProcessor() { return new FitterMessageProcessorImpl(); }

    @Bean
    RealtimeProcessor realtimeProcessor() { return new RealtimeProcessorImpl(); }

}
