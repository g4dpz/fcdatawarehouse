package com.badgersoft.datawarehouse.rawdata.controller;

import com.badgersoft.datawarehouse.common.utils.UTCClock;
import com.badgersoft.datawarehouse.rawdata.dao.UserDao;
import com.badgersoft.datawarehouse.rawdata.security.ReasonablePasswordGenerator;
import com.badgersoft.datawarehouse.rawdata.service.MailService;
import com.badgersoft.datawarehouse.rawdata.service.SatelliteListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    UserDao userDao;

    @Autowired
    ReasonablePasswordGenerator passwordGenerator;

    @Autowired
    MailService mailService;

    @Autowired
    UTCClock clock;

    private final SatelliteListService satelliteListService;

    @Autowired
    public RegistrationController(SatelliteListService satelliteListService) {
        this.satelliteListService = satelliteListService;
    }

    @GetMapping("registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView("content/registration");
        return modelAndView;
    }
}
