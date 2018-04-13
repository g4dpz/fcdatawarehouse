package com.badgersoft.datawarehouse.funcube.controller.rest;

import com.badgersoft.datawarehouse.common.controller.SatelliteControllerRest;
import com.badgersoft.datawarehouse.common.services.SatelliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SatelliteControllerRestImpl implements SatelliteControllerRest {

    private final SatelliteService satelliteService;

    @Autowired
    public SatelliteControllerRestImpl(@Qualifier("funcubeSatelliteService") SatelliteService satelliteService) {
        this.satelliteService = satelliteService;
    }
}
