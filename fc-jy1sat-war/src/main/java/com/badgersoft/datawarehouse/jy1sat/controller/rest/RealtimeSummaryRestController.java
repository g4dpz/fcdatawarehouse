package com.badgersoft.datawarehouse.jy1sat.controller.rest;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(value = "/data")
public class RealtimeSummaryRestController {

    @Autowired
    RealtimeDAO realtimeDAO;

    @Autowired
    SatelliteStatusDao satelliteStatusDao;

    @Autowired
    MinMaxDao minMaxDao;

    @RequestMapping(value = "/realtime", method = RequestMethod.GET, produces = "application/javascript")
    public String getSummary(@RequestParam(value = "callback") String callback) {
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

        List<String> contributors = Arrays.asList(statusEntity.getContributors().split("\\s*,\\s*"));

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
