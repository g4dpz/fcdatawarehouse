package com.badgersoft.datawarehouse.nayif1.controller.rest;

import com.badgersoft.datawarehouse.nayif1.service.RealtimeSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

@RestController
@RequestMapping(value = "/data")
public class RealtimeSummaryRestController {

    @Autowired
    RealtimeSummaryService realtimeSummaryService;

    @RequestMapping(value = "/realtime", method = RequestMethod.GET, produces = "application/javascript")
    public String getSummary(@RequestParam(value = "callback") String callback) {
        return realtimeSummaryService.getSummary(callback);
    }



    @RequestMapping(value = "/status", method = RequestMethod.GET, produces = "application/javascript")
    public String getStatus(@RequestParam(value = "callback") String callback) {

        return realtimeSummaryService.getStatus(callback);
    }
}
