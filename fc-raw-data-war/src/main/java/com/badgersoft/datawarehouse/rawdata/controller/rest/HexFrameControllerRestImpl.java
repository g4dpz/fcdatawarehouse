package com.badgersoft.datawarehouse.rawdata.controller.rest;

import com.badgersoft.datawarehouse.common.controller.HexFrameControllerRest;
import com.badgersoft.datawarehouse.common.services.HexFrameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HexFrameControllerRestImpl implements HexFrameControllerRest {

    private final HexFrameService hexFrameService;

    @Autowired
    public HexFrameControllerRestImpl(@Qualifier("rawdataHFS") HexFrameService hexFrameService) {
        this.hexFrameService = hexFrameService;
    }

    @PostMapping(value = "/data/hex/{site_id}")
    public ResponseEntity processFrame(@PathVariable(value = "site_id") String siteId, @RequestParam(value = "digest") String digest) {
        //LOG.debug("Raw hex frame received from: " + siteId);
        return ResponseEntity.ok().build();
    }
}
