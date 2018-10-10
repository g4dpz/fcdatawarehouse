package com.badgersoft.datawarehouse.rawdata.client;

public interface RawDataClient {

    String getFrame(String satelliteId, String sequqnceNumber, String frameId);
}
