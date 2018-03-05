package com.badgersoft.datawarehouse.nayif1.controller.rest;

import com.badgersoft.datawarehouse.common.controller.HexFrameControllerRest;
import com.badgersoft.datawarehouse.common.services.HexFrameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HexFrameControllerRestImpl implements HexFrameControllerRest {

    private final HexFrameService hexFrameService;

    @Autowired
    public HexFrameControllerRestImpl(@Qualifier("nayif1HFS") HexFrameService hexFrameService) {
        this.hexFrameService = hexFrameService;
    }
}
