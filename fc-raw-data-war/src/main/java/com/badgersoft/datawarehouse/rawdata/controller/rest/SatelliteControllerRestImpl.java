package com.badgersoft.datawarehouse.rawdata.controller.rest;

import com.badgersoft.datawarehouse.common.controller.SatelliteControllerRest;
import com.badgersoft.datawarehouse.common.dto.HexFrameDTO;
import com.badgersoft.datawarehouse.rawdata.service.HexFrameService;
import com.badgersoft.datawarehouse.rawdata.shared.Data;
import com.badgersoft.datawarehouse.rawdata.shared.Ranking;
import com.badgersoft.datawarehouse.rawdata.utils.BeanComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
public class SatelliteControllerRestImpl implements SatelliteControllerRest {

    private static final Logger LOG = LoggerFactory.getLogger(SatelliteControllerRestImpl.class);

    private final Lock lock = new ReentrantLock();

    private final HexFrameService hexFrameService;

    @Autowired
    public SatelliteControllerRestImpl(HexFrameService hexFrameService) {
        this.hexFrameService = hexFrameService;
    }

    @PostMapping(value = "/api/data/hex/{site_id}")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity processFrame(@PathVariable(value = "site_id") String siteId,
                                       @RequestParam(value = "digest") String digest,
                                       @RequestBody String body) {

        LOG.info("Raw hex frame received from: " + siteId);

        lock.lock();

        try {
            return hexFrameService.processHexFrame(siteId, digest, body);
        }
        finally {
            lock.unlock();
        }
    }

    @GetMapping(value = "/api/data/frame/{satelliteId}/{sequenceNumber}/{frameType}")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public HexFrameDTO getHexFrame(
            @PathVariable(value = "satelliteId") final String satelliteId,
            @PathVariable(value = "sequenceNumber") final String sequenceNumber,
            @PathVariable(value = "frameType") final String frameType,
            HttpServletRequest request, HttpServletResponse response) {
        final long seqNo = Long.parseLong(sequenceNumber);
        final long fType = Long.parseLong(frameType);
        HexFrameDTO frame = hexFrameService.getFrame(Long.parseLong(satelliteId), seqNo, fType);
        frame.setSequenceNumber(seqNo);
        frame.setFrameType(fType);
        if (frame != null) {
            return frame;
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

    @PostMapping(value = "/api/data/ranking")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Ranking getRanking(HttpServletRequest request) {
        int draw = Integer.parseInt(request.getParameter("draw"));
        int sort = Integer.parseInt(request.getParameter("order[0][column]"));
        sort *= request.getParameter("order[0][dir]").equals("asc") ? 1 : -1;
        Data data1 = new Data(1, "G4DPZ", 6000, 1000, 1000, 1000, 1000, 1000, 1000);
        Data data2 = new Data(2, "PA3WEG", 12000, 12000,0,0,0,0,0);
        List<Data> data = new ArrayList<>();
        data.add(data2);
        data.add(data1);
        data = sort(data, sort);
        return new Ranking(draw, data, sort);
    }

    private List<Data> sort(List<Data> data, int sort) {

        String[] sortAttributes
                = new String[] {"getTotal","getTotal","getSatellite1","getSatellite2","getSatellite3","getSatellite4","getSatellite5","getSatellite6"};

        BeanComparator bc = new BeanComparator(Data.class, sortAttributes[Math.abs(sort)], (sort < 0));
        Collections.sort(data, bc);
        return data;
    }

}
