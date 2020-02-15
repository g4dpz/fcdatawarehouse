package com.badgersoft.datawarehouse.jy1sat.service;

import com.badgersoft.datawarehouse.jy1sat.dao.MinMaxDao;
import com.badgersoft.datawarehouse.jy1sat.dao.RealtimeDAO;
import com.badgersoft.datawarehouse.jy1sat.dao.SatelliteStatusDao;
import com.badgersoft.datawarehouse.jy1sat.domain.MinMaxEntity;
import com.badgersoft.datawarehouse.jy1sat.domain.RealtimeEntity;
import com.badgersoft.datawarehouse.jy1sat.domain.SatelliteStatusEntity;
import com.badgersoft.datawarehouse.jy1sat.dto.RealtimeDTO;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    RealtimeDAO realtimeDAO;

    @Autowired
    SatelliteStatusDao satelliteStatusDao;

    @Autowired
    MinMaxDao minMaxDao;

    @Override
    public String getSummary(String callback) {
        SatelliteStatusEntity statusEntity = (SatelliteStatusEntity) (satelliteStatusDao.findAll().iterator().next());
        Long sequenceNumber = statusEntity.getSequenceNumber();
        Long frameType = statusEntity.getFrameType();

        RealtimeEntity realtimeEntity = realtimeDAO.findBySequenceNumberAndFrameType(sequenceNumber, frameType);

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
        Map<String, Object> map = new HashMap<String, Object>();


        final String contributorString  = statusEntity.getContributors();

        List<String> contributors = new ArrayList<>();

        if (contributorString != null) {
            contributors = Arrays.asList(contributorString.split("\\s*,\\s*"));
        }
        else {
            LOG.error("No contributors found");
        }

        map.put("data", new RealtimeDTO(realtimeEntity, minima, maxima, contributors, statusEntity.getPacketCount()));

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
