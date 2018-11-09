package com.badgersoft.datawarehouse.rawdata.controller;

import com.badgersoft.datawarehouse.rawdata.domain.Satellite;
import com.badgersoft.datawarehouse.rawdata.service.SatelliteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MissionController {

    private final SatelliteListService satelliteListService;

    @Autowired
    public MissionController(SatelliteListService satelliteListService) {
        this.satelliteListService = satelliteListService;
    }

    @GetMapping("missions")
    public ModelAndView listSatellites() {
        List<Satellite> allSatellites = satelliteListService.findAllSatellites();

        ModelAndView modelAndView = new ModelAndView("content/satellite-list");
        modelAndView.addObject("allSatellites", allSatellites);
        return modelAndView;
    }
}
