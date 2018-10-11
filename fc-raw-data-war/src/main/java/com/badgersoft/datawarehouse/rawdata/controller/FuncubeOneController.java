package com.badgersoft.datawarehouse.rawdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FuncubeOneController {

    @GetMapping("/ui/fc1-fm")
    public ModelAndView display() {
        ModelAndView modelAndView = new ModelAndView("content/fc1-fm/index");
        return modelAndView;
    }

}
