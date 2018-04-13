package com.badgersoft.datawarehouse.rawdata.controller.rest;

import com.badgersoft.datawarehouse.common.controller.SatelliteControllerRest;
import com.badgersoft.datawarehouse.common.dto.HexFrameDTO;
import com.badgersoft.datawarehouse.rawdata.service.HexFrameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class SatelliteControllerRestImpl implements SatelliteControllerRest {

    private static final Logger LOG = LoggerFactory.getLogger(SatelliteControllerRestImpl.class);

    private final HexFrameService hexFrameService;

    @Autowired
    public SatelliteControllerRestImpl(HexFrameService hexFrameService) {
        this.hexFrameService = hexFrameService;
    }

    @PostMapping(value = "/data/hex/{site_id}")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity processFrame(@PathVariable(value = "site_id") String siteId,
                                       @RequestParam(value = "digest") String digest,
                                       @RequestBody String body) {

        LOG.info("Raw hex frame received from: " + siteId);
        return hexFrameService.processHexFrame(siteId, digest, body);
    }

    @GetMapping(value = "/frame/{satelliteId}/{sequenceNumber}/{frameType}")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public HexFrameDTO getHexFrame(
            @PathVariable(value = "satelliteId") final String satelliteId,
            @PathVariable(value = "sequenceNumber") final String sequenceNumber,
            @PathVariable(value = "frameType") final String frameType,
            HttpServletRequest request, HttpServletResponse response) {
        HexFrameDTO frame = hexFrameService.getFrame(Long.parseLong(satelliteId), Long.parseLong(sequenceNumber), Long.parseLong(frameType));
        if (frame != null) {
            return frame;
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }
}
