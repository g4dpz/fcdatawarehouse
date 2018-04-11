package com.badgersoft.datawarehouse.common.services;

/**
 * Created by davidjohnson on 14/08/2016.
 */
public interface SatelliteService {
    String ping();
    String handleFrameMessage(String message);
    void handleUserMessage(String message);
}
