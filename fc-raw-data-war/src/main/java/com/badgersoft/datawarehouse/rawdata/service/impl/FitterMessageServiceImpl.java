package com.badgersoft.datawarehouse.rawdata.service.impl;

import com.badgersoft.datawarehouse.common.dto.FitterMessageDTO;
import com.badgersoft.datawarehouse.common.dto.FitterMessagesDTO;
import com.badgersoft.datawarehouse.common.dto.ValMinMaxDTO;
import com.badgersoft.datawarehouse.rawdata.service.FitterMessageService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FitterMessageServiceImpl implements FitterMessageService {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private final SimpleDateFormat SDF = new SimpleDateFormat(DATE_FORMAT);

    @Override
    public List<ValMinMaxDTO> getMessages(String url) {



        List<ValMinMaxDTO> minMaxValues  = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<FitterMessagesDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<FitterMessagesDTO>(){});

        FitterMessagesDTO fitterMessagesDTO = response.getBody();

        List<ValMinMaxDTO> messages = new ArrayList<>();

        for (FitterMessageDTO fitterMessageDTO : fitterMessagesDTO.getFitterMessages()) {
            final String messageText = fitterMessageDTO.getMessageText();
            final String shortText = messageText.length() > 70 ? messageText.substring(0, 70) : null;
            messages.add(new ValMinMaxDTO(messageText, fitterMessageDTO.getLastReceived(),
                    shortText, fitterMessageDTO.getSlot()));
        }

        return messages;
    }
}
