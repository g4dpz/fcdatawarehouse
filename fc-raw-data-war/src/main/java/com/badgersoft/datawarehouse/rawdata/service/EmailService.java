package com.badgersoft.datawarehouse.rawdata.service;

import com.badgersoft.datawarehouse.rawdata.messaging.Mail;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {
    void sendRegistrationMessage(Mail mail) throws MessagingException, IOException;
}
