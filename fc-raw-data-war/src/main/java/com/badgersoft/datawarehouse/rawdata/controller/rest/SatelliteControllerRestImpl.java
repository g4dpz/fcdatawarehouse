package com.badgersoft.datawarehouse.rawdata.controller.rest;

import com.badgersoft.datawarehouse.common.controller.SatelliteControllerRest;
import com.badgersoft.datawarehouse.common.dto.HexFrameDTO;
import com.badgersoft.datawarehouse.rawdata.service.HexFrameService;
import com.badgersoft.datawarehouse.rawdata.service.UserRankingService;
import com.badgersoft.datawarehouse.rawdata.shared.Ranking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
public class SatelliteControllerRestImpl implements SatelliteControllerRest {

    private static final Logger LOG = LoggerFactory.getLogger(SatelliteControllerRestImpl.class);

    private final Lock lock = new ReentrantLock();

    private final HexFrameService hexFrameService;
    private UserRankingService userRankingService;

    @Autowired
    public SatelliteControllerRestImpl(HexFrameService hexFrameService, UserRankingService userRankingService) {
        this.hexFrameService = hexFrameService;
        this.userRankingService = userRankingService;
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
        if (frame != null) {
            frame.setSequenceNumber(seqNo);
            frame.setFrameType(fType);
            return frame;
        }
        else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @PostMapping(value = "/api/data/ranking")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Ranking getRanking(HttpServletRequest request) {
        int draw = Integer.parseInt(request.getParameter("draw"));
        int sort = Integer.parseInt(request.getParameter("order[0][column]"));
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        String search = request.getParameter("search[value]");

        sort *= request.getParameter("order[0][dir]").equals("asc") ? 1 : -1;

        Ranking ranking = userRankingService.getRanking(draw, sort, start, length, search);

        return ranking;
    }

    @GetMapping(value = "/api/data/payload/{satelliteId}/{sequenceNumber}")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<String> getPayloads(@PathVariable(value = "satelliteId") Long satelliteId,
                                    @PathVariable(value = "sequenceNumber") Long sequenceNumber,
                                    @RequestParam(value = "frames") String frames,
                                    HttpServletRequest request, HttpServletResponse response) {

        final List<String> payloads = hexFrameService.getPayloads(satelliteId, sequenceNumber, frames);

        if (payloads == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        else {
            return payloads;
        }
    }

}
