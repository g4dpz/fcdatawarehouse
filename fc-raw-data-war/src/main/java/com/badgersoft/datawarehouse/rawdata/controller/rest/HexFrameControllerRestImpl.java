package com.badgersoft.datawarehouse.rawdata.controller.rest;

import com.badgersoft.datawarehouse.common.controller.HexFrameControllerRest;
import com.badgersoft.datawarehouse.common.services.HexFrameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class HexFrameControllerRestImpl implements HexFrameControllerRest {

    private static final Logger LOG = LoggerFactory.getLogger(HexFrameControllerRestImpl.class);

    private final HexFrameService hexFrameService;

    @Autowired
    public HexFrameControllerRestImpl(@Qualifier("rawdataHFS") HexFrameService hexFrameService) {
        this.hexFrameService = hexFrameService;
    }

    @PreAuthorize("hasPermission(#replyToMessageId, 'Message', 'read')")
    @PostMapping(value = "/data/hex/{site_id}")
    public ResponseEntity processFrame(@PathVariable(value = "site_id") String siteId,
                                       @RequestParam(value = "digest") String digest,
                                       @RequestBody String body) {

        LOG.info("Raw hex frame received from: " + siteId);
        return ResponseEntity.ok().build();
    }
}
