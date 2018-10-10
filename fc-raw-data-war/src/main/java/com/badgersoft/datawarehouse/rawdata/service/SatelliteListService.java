package com.badgersoft.datawarehouse.rawdata.service;

import com.badgersoft.datawarehouse.rawdata.domain.Satellite;

import java.util.List;

public interface SatelliteListService {
    List<Satellite> findAllSatellites();
}
