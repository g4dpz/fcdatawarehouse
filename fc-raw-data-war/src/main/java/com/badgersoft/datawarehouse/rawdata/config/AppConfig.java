package com.badgersoft.datawarehouse.rawdata.config;

import com.badgersoft.datawarehouse.common.services.HexFrameService;
import com.badgersoft.datawarehouse.common.utils.UTCClock;
import com.badgersoft.datawarehouse.rawdata.service.HexFrameServiceImpl;
import com.badgersoft.datawarehouse.rawdata.service.JmsMessageSender;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

@Configuration
@ComponentScans(value = { @ComponentScan("com.badgersoft.datawarehouse.rawdata.controller"),
        @ComponentScan("com.badgersoft.datawarehouse.rawdata.service") })
public class AppConfig {

    @Bean
    HexFrameService hexFrameService() { return new HexFrameServiceImpl(); }

    @Bean
    UTCClock clock() { return new UTCClock(); }

    @Bean
    JmsMessageSender jmsMessageSender() {
        return new JmsMessageSender();
    }


    @Bean
    ActiveMQConnectionFactory amqConnectionFactory() {
        return new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
    }

    @Bean
    CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(amqConnectionFactory());
    }

    @Bean
    ActiveMQQueue defaultDestination() {
        return new ActiveMQQueue("frame_processed");
    }

    @Bean
    JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestination(defaultDestination());
        jmsTemplate.setConnectionFactory(connectionFactory());
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(hexFrameService());
    }

    @Bean
    DefaultMessageListenerContainer messageListenerContainer() {
        DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
        defaultMessageListenerContainer.setConnectionFactory(connectionFactory());
        defaultMessageListenerContainer.setDestinationName("frame_processed");
        defaultMessageListenerContainer.setMessageListener(messageListenerAdapter());
        return defaultMessageListenerContainer;
    }

    @Bean
    EnvConfig envConfig() { return new EnvConfigImpl(); }

}
