package com.badgersoft.datawarehouse.common.services;

import org.springframework.http.ResponseEntity;

public interface HexFrameService {

    String ping();

    ResponseEntity processHexFrame(String site_id, String digest, String hex_frame);
}
