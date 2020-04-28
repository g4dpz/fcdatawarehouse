package com.badgersoft.datawarehouse.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class GmailTest {

    public static void main(String... args) {

        GmailTest test = new GmailTest();
        JavaMailSender sender = test.getJavaMailSender();
        test.sendSimpleMessage(sender, "dave@g4dpz.me.uk", "foo", "bar");

    }

    private JavaMailSender getJavaMailSender() {
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

        return mailSender;
    }

    public void sendSimpleMessage(JavaMailSender sender,
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        sender.send(message);
    }

}
