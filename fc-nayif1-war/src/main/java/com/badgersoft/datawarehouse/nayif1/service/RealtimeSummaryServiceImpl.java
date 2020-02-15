package com.badgersoft.datawarehouse.nayif1.service;

import com.badgersoft.datawarehouse.nayif1.dao.MinMaxDao;
import com.badgersoft.datawarehouse.nayif1.dao.RealtimeDAO;
import com.badgersoft.datawarehouse.nayif1.dao.SatelliteStatusDao;
import com.badgersoft.datawarehouse.nayif1.domain.MinMaxEntity;
import com.badgersoft.datawarehouse.nayif1.domain.RealtimeEntity;
import com.badgersoft.datawarehouse.nayif1.domain.SatelliteStatusEntity;
import com.badgersoft.datawarehouse.nayif1.dto.RealtimeDTO;
import com.badgersoft.datawarehouse.nayif1.dto.SatelliteStatusDTO;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RealtimeSummaryServiceImpl implements RealtimeSummaryService {

    private static Logger LOG = LoggerFactory.getLogger(RealtimeSummaryServiceImpl.class.getName());

    static final SimpleDateFormat SDTF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");

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

    @Override
    public String getStatus(String callback) {

        SatelliteStatusEntity satelliteStatus = satelliteStatusDao.findAll().iterator().next();

        List<String> latestContributors = Arrays.asList(satelliteStatus.getContributors().split("\\s*,\\s*"));
        String lastReceived = SDTF.format(satelliteStatus.getLastUpdated());
        String latestSequenceNumber = String.valueOf(satelliteStatus.getSequenceNumber());
        String packetCount = convertPacketCount(String.valueOf(satelliteStatus.getPacketCount()));
        String modeSwitching = satelliteStatus.getMode();
        String transponderState = satelliteStatus.getTransponderState();
        String latitude = convertLatitude(satelliteStatus.getLatitude());
        String longitude = convertLongitude(satelliteStatus.getLongitude());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("data", new SatelliteStatusDTO(latestContributors,
                lastReceived, latestSequenceNumber, packetCount, modeSwitching, transponderState, latitude, longitude));

        try {
            return objectMapper.writeValueAsString(new JSONPObject(callback, map));
        } catch (JsonGenerationException e) {
            return callback + "([error:" + e.getMessage() + "]);";
        } catch (JsonMappingException e) {
            return callback + "([error:" + e.getMessage() + "]);";
        } catch (IOException e) {
            return callback + "([error:" + e.getMessage() + "]);";
        }
    }

    private String convertLatitude(String latitude) {
        final double latitudeValue = Double.parseDouble((latitude != null && !latitude.trim().isEmpty()) ? latitude : "0");
        if (latitudeValue > 0.0) {
            latitude = String.format("%5.1f N", latitudeValue);
        } else {
            latitude = String.format("%5.1f S", Math.abs(latitudeValue));
        }

        return latitude;
    }


    private String convertLongitude(String longitude) {
        final double longitudeValue = Double.parseDouble((longitude != null && !longitude.trim().isEmpty()) ? longitude : "0");
        if (longitudeValue > 180.0) {
            longitude = String.format("%5.1f W", 360.0 - longitudeValue);
        } else {
            longitude = String.format("%5.1f E", longitudeValue);
        }

        return longitude;
    }

    private String convertPacketCount(String packetCount) {
        long packets = Long.parseLong(packetCount);
        double size = (packets * 256) / 1.0e6;
        return String.format("%d (%.1f MB)", packets, size);
    }
}
