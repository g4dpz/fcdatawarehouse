package com.badgersoft.datawarehouse.rawdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NayifOneController {

    @GetMapping("/ui/nayif1")
    public ModelAndView display() {
        ModelAndView modelAndView = new ModelAndView("content/nayif1/index");
        return modelAndView;
    }

}
