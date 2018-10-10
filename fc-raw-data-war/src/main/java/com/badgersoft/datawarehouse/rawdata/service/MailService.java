package com.badgersoft.datawarehouse.rawdata.service;

import java.util.Map;

public interface MailService {
    void sendMail(String from, String to, String subject, String body);

    void sendAlertMail(String alert);

    void sendUsingTemplate(String toAddress, Map<String, Object> emailTags, String template);
}
