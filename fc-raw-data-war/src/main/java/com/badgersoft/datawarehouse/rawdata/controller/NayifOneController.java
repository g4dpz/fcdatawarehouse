package com.badgersoft.datawarehouse.rawdata.controller;

import com.badgersoft.datawarehouse.rawdata.service.FitterMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NayifOneController {

    private final FitterMessageService fitterMessageService;

    @Autowired
    public NayifOneController(FitterMessageService fitterMessageService) {
        this.fitterMessageService = fitterMessageService;
    }

    @GetMapping("/ui/nayif1")
    public ModelAndView display() {
        ModelAndView modelAndView = new ModelAndView("content/nayif1/index");
        return modelAndView;
    }

    @GetMapping("/ui/nayif1/wod")
    public ModelAndView wod() {
        ModelAndView modelAndView = new ModelAndView("content/nayif1/wod");
        return modelAndView;
    }

    @GetMapping("/ui/nayif1/fitter")
    public ModelAndView fitter() {
        ModelAndView modelAndView = new ModelAndView("content/nayif1/fitter");
        modelAndView.addObject("fitterMessages", fitterMessageService.getMessages("http://localhost:10080/nayif1/data/fitter"));
        return modelAndView;
    }

}
