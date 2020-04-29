package com.badgersoft.datawarehouse.rawdata.service;

import com.badgersoft.datawarehouse.common.dto.HexFrameDTO;
import com.badgersoft.datawarehouse.rawdata.service.impl.PacketResponseEntity;

import java.util.List;

public interface HexFrameService {

    String ping();

    PacketResponseEntity processHexFrame(String site_id, String digest, String hex_frame);

    void handleMessage(String message);

    HexFrameDTO getFrame(long l, long l1, long l2);

    List<String> getPayloads(Long satelliteId, Long sequenceNumber, String frames);
}
