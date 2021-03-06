package com.badgersoft.datawarehouse.rawdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"","/"})
public class LandingPageController {

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("redirect:missions");
    }
}
