package com.badgersoft.datawarehouse.rawdata.controller;

import com.badgersoft.datawarehouse.rawdata.service.SatelliteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RankingController {

    private final SatelliteListService satelliteListService;

    @Autowired
    public RankingController(SatelliteListService satelliteListService) {
        this.satelliteListService = satelliteListService;
    }

    @GetMapping("ranking")
    public ModelAndView ranking() {

        ModelAndView modelAndView = new ModelAndView("content/ranking");
        return modelAndView;
    }
}
