package com.badgersoft.datawarehouse.funcube.controller.rest;

import com.badgersoft.datawarehouse.common.dto.FitterMessageDTO;
import com.badgersoft.datawarehouse.common.dto.FitterMessagesDTO;
import com.badgersoft.datawarehouse.funcube.dao.FitterMessageDao;
import com.badgersoft.datawarehouse.funcube.domain.FitterMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequestMapping(value = "/data")
public class FitterRestController {

    static final TimeZone TZ = TimeZone.getTimeZone("UTC");

    static final SimpleDateFormat SDTF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");

    @Autowired
    FitterMessageDao fitterMessageDAO;

    @RequestMapping(value = "/fitter", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public FitterMessagesDTO getSummary()
    {
        long now = Calendar.getInstance(TZ).getTimeInMillis();

        Timestamp oneWeekAgo = new Timestamp(now - (14 * 24 * 60 * 60 * 1000));

        List<FitterMessageEntity> fitterMessageEntities = fitterMessageDAO.getNoneDebugReceivedAfter(oneWeekAgo);

        FitterMessagesDTO fitterMessagesDTO = new FitterMessagesDTO();

        if (fitterMessageEntities != null && !fitterMessageEntities.isEmpty()) {

            List<FitterMessageDTO> messages = new ArrayList<FitterMessageDTO>();

            for (FitterMessageEntity fitterMessageEntity : fitterMessageEntities) {
                messages.add(
                        new FitterMessageDTO(
                                fitterMessageEntity.getMessageText(),
                                fitterMessageEntity.getSlot(),
                                SDTF.format(fitterMessageEntity.getLastReceived())));
            }

            fitterMessagesDTO.setFitterMessages(messages);
        }

        return fitterMessagesDTO;

    }
}