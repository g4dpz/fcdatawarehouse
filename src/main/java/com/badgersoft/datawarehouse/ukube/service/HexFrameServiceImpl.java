package com.badgersoft.datawarehouse.ukube.service;

import com.badgersoft.datawarehouse.common.services.HexFrameService;
import org.springframework.stereotype.Service;

@Service
public class HexFrameServiceImpl implements HexFrameService {

    public String ping() {
        return "Hello";
    }
}
