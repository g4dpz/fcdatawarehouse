package com.badgersoft.datawarehouse.rawdata.controller;

import com.badgersoft.datawarehouse.rawdata.service.SatelliteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StatusController {

    private final SatelliteListService satelliteListService;

    @Autowired
    public StatusController(SatelliteListService satelliteListService) {
        this.satelliteListService = satelliteListService;
    }

    @GetMapping("status")
    public ModelAndView status() {
        ModelAndView modelAndView = new ModelAndView("content/status");
        return modelAndView;
    }
}
