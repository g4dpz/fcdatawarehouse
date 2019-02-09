package com.badgersoft.datawarehouse.rawdata.controller;

import com.badgersoft.datawarehouse.rawdata.service.FitterMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JY1SatController {

    private final FitterMessageService fitterMessageService;

    @Autowired
    public JY1SatController(FitterMessageService fitterMessageService) {
        this.fitterMessageService = fitterMessageService;
    }

    @GetMapping("/ui/jy1sat-fm")
    public ModelAndView display() {
        ModelAndView modelAndView = new ModelAndView("content/jy1sat-fm/index");
        return modelAndView;
    }

    @GetMapping("/ui/jy1sat-fm/wod")
    public ModelAndView wod() {
        ModelAndView modelAndView = new ModelAndView("content/jy1sat-fm/wod");
        return modelAndView;
    }

    @GetMapping("/ui/jy1sat-fm/fitter")
    public ModelAndView fitter() {
        ModelAndView modelAndView = new ModelAndView("content/jy1sat-fm/fitter");
        modelAndView.addObject("fitterMessages", fitterMessageService.getMessages());
        return modelAndView;
    }

}
