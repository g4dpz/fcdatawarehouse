package com.badgersoft.datawarehouse.nayif1.service;

import com.badgersoft.datawarehouse.common.services.AbstractSatelliteService;
import com.badgersoft.datawarehouse.common.services.SatelliteService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "nayif1SatelliteService")
public class SatelliteServiceImpl extends AbstractSatelliteService implements SatelliteService {
}
