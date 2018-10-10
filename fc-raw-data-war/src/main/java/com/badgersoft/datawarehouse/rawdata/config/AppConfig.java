package com.badgersoft.datawarehouse.rawdata.config;

import com.badgersoft.datawarehouse.common.utils.UTCClock;
import com.badgersoft.datawarehouse.rawdata.security.ReasonablePasswordGenerator;
import com.badgersoft.datawarehouse.rawdata.service.HexFrameService;
import com.badgersoft.datawarehouse.rawdata.service.MailService;
import com.badgersoft.datawarehouse.rawdata.service.SatelliteListService;
import com.badgersoft.datawarehouse.rawdata.service.impl.HexFrameServiceImpl;
import com.badgersoft.datawarehouse.rawdata.service.impl.MailServiceImpl;
import com.badgersoft.datawarehouse.rawdata.service.impl.SatelliteListServiceImpl;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ComponentScans(value = { @ComponentScan("com.badgersoft.datawarehouse.rawdata") })
public class AppConfig {

    @Bean
    HexFrameService hexFrameService() { return new HexFrameServiceImpl(); }

    @Bean
    UTCClock clock() { return new UTCClock(); }

    @Bean
    EnvConfig envConfig() { return new EnvConfigImpl(); }

    @Bean
    SatelliteListService satelliteListService() { return new SatelliteListServiceImpl(); }

    @Bean
    ReasonablePasswordGenerator reasonablePasswordGenerator() { return new ReasonablePasswordGenerator(); }

    @Bean
    MailService mailService()  { return new MailServiceImpl(); }

    @Bean
    MailSender mailSender() { return new JavaMailSenderImpl(); }

    @Bean
    SimpleMailMessage alertMailMessage() { return new SimpleMailMessage(); }

    @Bean
    VelocityEngine velocityEngine() { return new VelocityEngine(); }

}
