package com.badgersoft.datawarehouse.funcube.controller.rest;

import com.badgersoft.datawarehouse.funcube.dto.WodDTO;
import com.badgersoft.datawarehouse.funcube.service.WODService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WODRestController {


    @Autowired
    WODService wodService;

    @RequestMapping(value = "/data/wod", method = RequestMethod.GET, produces = "application/javascript")
    public WodDTO getLatestWOD() {
        return wodService.getLatestWOD();
    }
}
