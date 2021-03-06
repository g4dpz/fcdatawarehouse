package com.badgersoft.datawarehouse.rawdata.config;

import com.badgersoft.datawarehouse.common.utils.UTCClock;
import com.badgersoft.datawarehouse.rawdata.security.ReasonablePasswordGenerator;
import com.badgersoft.datawarehouse.rawdata.service.*;
import com.badgersoft.datawarehouse.rawdata.service.impl.*;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

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
    EmailService mailService()  { return new EmailServiceImpl(); }

    @Bean
    JavaMailSender emailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("operations@funcube.org.uk");
        mailSender.setPassword("H4les0wen1234!");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return mailSender; }

    @Bean
    SimpleMailMessage alertMailMessage() { return new SimpleMailMessage(); }

    @Bean
    VelocityEngine velocityEngine() { return new VelocityEngine(); }

    @Bean
    UserRankingService userRankingService() { return new UserRankingServiceImpl(); }

    @Bean
    FitterMessageService fitterMessageService() { return new FitterMessageServiceImpl(); }

}
