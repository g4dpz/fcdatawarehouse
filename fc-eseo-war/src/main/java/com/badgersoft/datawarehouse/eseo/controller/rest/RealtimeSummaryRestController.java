package com.badgersoft.datawarehouse.eseo.controller.rest;

import com.badgersoft.datawarehouse.eseo.service.RealtimeSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/data")
public class RealtimeSummaryRestController {

    @Autowired
    RealtimeSummaryService realtimeSummaryService;

    @RequestMapping(value = "/realtime", method = RequestMethod.GET, produces = "application/javascript")
    public String getSummary(@RequestParam(value = "callback") String callback) {
        return realtimeSummaryService.getSummary(callback);
    }
}
