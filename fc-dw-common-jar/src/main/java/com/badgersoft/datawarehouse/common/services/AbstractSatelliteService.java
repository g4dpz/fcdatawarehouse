package com.badgersoft.datawarehouse.common.services;

public class AbstractSatelliteService implements SatelliteService {

    @Override
    public String ping() {
        return "Hello";
    }

    @Override
    public String handleFrameMessage(String message) {
        return null;
    }

    @Override
    public void handleUserMessage(String message) {

    }
}
