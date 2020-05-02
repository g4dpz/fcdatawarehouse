package com.badgersoft.datawarehouse.funcube.config;

import com.badgersoft.datawarehouse.funcube.processor.*;
import com.badgersoft.datawarehouse.funcube.service.BackfillRealtimeService;
import com.badgersoft.datawarehouse.funcube.service.BackfillRealtimeServiceImpl;
import com.badgersoft.datawarehouse.funcube.service.RealtimeSummaryService;
import com.badgersoft.datawarehouse.funcube.service.RealtimeSummaryServiceImpl;
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
    WodProcessor wodProcessor() { return new WodProcessorImpl(); }

    @Bean
    HighResProcessor highResProcessor() { return new HighResProcessorImpl(); }

    @Bean
    FitterMessageProcessor fitterMessageProcessor() { return new FitterMessageProcessorImpl(); }

    @Bean
    RealtimeProcessor realtimeProcessor() { return new RealtimeProcessorImpl(); }

    @Bean
    RealtimeSummaryService realtimeSummaryService() { return new RealtimeSummaryServiceImpl(); }

    @Bean
    BackfillRealtimeService backfillRealtimeService() { return new BackfillRealtimeServiceImpl(); }

}
