package com.badgersoft.datawarehouse.common.services;

import org.springframework.http.ResponseEntity;

public abstract class AbstractHexFrameService implements HexFrameService {

    public String ping() {
        return "Hello";
    }

    public ResponseEntity processHexFrame(String siteId, String digest, String body) {
        return null;
    }
}
