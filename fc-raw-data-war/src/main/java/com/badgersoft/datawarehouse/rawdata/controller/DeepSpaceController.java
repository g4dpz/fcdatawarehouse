package com.badgersoft.datawarehouse.rawdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DeepSpaceController {

    @GetMapping("/ui/deepspace")
    public ModelAndView display() {
        ModelAndView modelAndView = new ModelAndView("content/deepspace/index");
        return modelAndView;
    }

}
