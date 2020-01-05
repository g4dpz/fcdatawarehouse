package com.badgersoft.datawarehouse.jy1sat.controller.rest;

import com.badgersoft.datawarehouse.jy1sat.service.RealtimeSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/data")
public class RealtimeRestController {

    @Autowired
    RealtimeSummaryService realtimeSummaryService;

    @RequestMapping(value = "/realtime", method = RequestMethod.GET, produces = "application/javascript")
    public String getRealtime(@RequestParam(value = "callback") String callback) {
        return realtimeSummaryService.getSummary(callback);
    }
}
