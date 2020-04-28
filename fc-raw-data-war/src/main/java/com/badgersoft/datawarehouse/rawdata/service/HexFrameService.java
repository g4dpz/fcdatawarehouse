package com.badgersoft.datawarehouse.rawdata.service;

import com.badgersoft.datawarehouse.common.dto.HexFrameDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HexFrameService {

    String ping();

    void handleMessage(String message);

    HexFrameDTO getFrame(long l, long l1, long l2);

    List<String> getPayloads(Long satelliteId, Long sequenceNumber, String frames);

    ResponseEntity processFrame(String siteId, String digest, String body);
}
