package com.badgersoft.datawarehouse.rawdata.service.impl;

import com.badgersoft.datawarehouse.rawdata.domain.Satellite;
import com.badgersoft.datawarehouse.rawdata.service.SatelliteListService;

import java.util.ArrayList;
import java.util.List;

public class SatelliteListServiceImpl implements SatelliteListService {

    @Override
    public List<Satellite> findAllSatellites() {
        List<Satellite> satellites = new ArrayList<>();
        satellites.add(new Satellite("ESEO", "eseo", "eseo_200.png", true));
        satellites.add(new Satellite("FUNcube-1 (Flight)", "fc1-fm", "funcube-1_200.png", true));
        satellites.add(new Satellite("JY1Sat (Flight)", "jy1sat-fm", "jy1sat_200.png", true));
        satellites.add(new Satellite("Nayif-1", "nayif1", "nayif-1_200.png", true));
        satellites.add(new Satellite("UKube-1", "ukube1", "ukube-1_200.png", true));
        return satellites;
    }
}
