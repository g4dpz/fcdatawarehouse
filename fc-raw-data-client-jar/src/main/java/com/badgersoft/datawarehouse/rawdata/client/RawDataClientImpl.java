package com.badgersoft.datawarehouse.rawdata.client;

public class RawDataClientImpl implements RawDataClient {

    private String host;
    private String port;

    public RawDataClientImpl(String host, String port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public String getFrame(String satelliteId, String sequqnceNumber, String frameId) {
        return null;
    }
}
