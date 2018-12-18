package com.badgersoft.datawarehouse.rawdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JY1SatController {

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

}
