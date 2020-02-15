package com.badgersoft.datawarehouse.eseo.service;

import com.badgersoft.datawarehouse.eseo.dao.*;
import com.badgersoft.datawarehouse.eseo.domain.*;
import com.badgersoft.datawarehouse.eseo.dto.RealtimeDTO;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class RealtimeSummaryServiceImpl implements RealtimeSummaryService {

    private static Logger LOG = LoggerFactory.getLogger(RealtimeSummaryServiceImpl.class.getName());

    @Autowired
    RealtimeDao realtimeDAO;

    @Autowired
    PayloadOneDao payloadOneDao;

    @Autowired
    PayloadTwoDao payloadTwoDao;

    @Autowired
    SatelliteStatusDao satelliteStatusDao;

    @Autowired
    MinMaxDao minMaxDao;

    @Override
    public String getSummary(String callback) {
        SatelliteStatusEntity statusEntity = (SatelliteStatusEntity) (satelliteStatusDao.findAll().iterator().next());
        Long sequenceNumberOne = statusEntity.getSequenceNumber();
        Long frameTypeOne = statusEntity.getFrameType();
        Long sequenceNumberTwo = statusEntity.getSequenceNumberTwo();
        Long frameTypeTwo = statusEntity.getFrameTypeTwo();

        Long sequenceNumber;
        Long frameType;

        if (sequenceNumberOne > sequenceNumberTwo) {
            sequenceNumber = sequenceNumberOne;
            frameType = frameTypeOne;
        }
        else {
            sequenceNumber = sequenceNumberTwo;
            frameType = frameTypeTwo;
        }

        RealtimeEntity realtimeEntity = realtimeDAO.findBySequenceNumberAndFrameType(sequenceNumber, frameType);
        PayloadOneEntity payloadOneEntity = payloadOneDao.findBySequenceNumberAndFrameType(sequenceNumberOne, frameTypeOne);
        PayloadTwoEntity payloadTwoEntity = payloadTwoDao.findBySequenceNumberAndFrameType(sequenceNumberTwo, frameTypeTwo);

        if (realtimeEntity == null) {
            return callback + "([error:" + "No data found" + "]);";
        }

        List<MinMaxEntity> minMaxEntityList = minMaxDao.getAllEnabled();
        List<Double> minima = new ArrayList();
        List<Double> maxima = new ArrayList();
        for (MinMaxEntity minMaxEntity : minMaxEntityList) {
            minima.add(minMaxEntity.getMinimum());
            maxima.add(minMaxEntity.getMaximum());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        Map<String, Object> map = new HashMap<String, Object>();

        final String contributorString  = statusEntity.getContributors();

        List<String> contributors = new ArrayList<>();

        if (contributorString != null) {
            contributors = Arrays.asList(contributorString.split("\\s*,\\s*"));
        }
        else {
            LOG.error("No contributors found");
        }

        map.put("data", new RealtimeDTO(realtimeEntity, minima, maxima, contributors, statusEntity.getPacketCount(), payloadOneEntity, payloadTwoEntity));

        try {
            return objectMapper.writeValueAsString(new JSONPObject(callback, map));
        }
        catch (JsonGenerationException e) {
            return callback + "([error:" + e.getMessage() + "]);";
        }
        catch (JsonMappingException e) {
            return callback + "([error:" + e.getMessage() + "]);";
        }
        catch (IOException e) {
            return callback + "([error:" + e.getMessage() + "]);";
        }
    }
}
