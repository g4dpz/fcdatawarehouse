package com.badgersoft.datawarehouse.jy1sat.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidjohnson on 18/01/2017.
 */
public class SatelliteStatusDTO implements Serializable {

    private List<String> latestContibutors = new ArrayList<>();
    private String lastReceived;
    private String latestSequenceNumber;
    private String packetCount;
    private String modeSwitching;
    private String transponderState;
    private String latitude;
    private String longitude;

    public SatelliteStatusDTO() {
    }

    public SatelliteStatusDTO(List<String> latestContibutors, String lastReceived,
                              String latestSequenceNumber, String packetCount,
                              String modeSwitching, String transponderState,
                              String latitude, String longitude) {
        this.latestContibutors = latestContibutors;
        this.lastReceived = lastReceived;
        this.latestSequenceNumber = latestSequenceNumber;
        this.packetCount = packetCount;
        this.modeSwitching = modeSwitching;
        this.transponderState = transponderState;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public List<String> getLatestContibutors() {
        return latestContibutors;
    }

    public void setLatestContibutors(List<String> latestContibutors) {
        this.latestContibutors = latestContibutors;
    }

    public String getLastReceived() {
        return lastReceived;
    }

    public void setLastReceived(String lastReceived) {
        this.lastReceived = lastReceived;
    }

    public String getLatestSequenceNumber() {
        return latestSequenceNumber;
    }

    public void setLatestSequenceNumber(String latestSequenceNumber) {
        this.latestSequenceNumber = latestSequenceNumber;
    }

    public String getPacketCount() {
        return packetCount;
    }

    public void setPacketCount(String packetCount) {
        this.packetCount = packetCount;
    }

    public String getModeSwitching() {
        return modeSwitching;
    }

    public void setModeSwitching(String modeSwitching) {
        this.modeSwitching = modeSwitching;
    }

    public String getTransponderState() {
        return transponderState;
    }

    public void setTransponderState(String transponderState) {
        this.transponderState = transponderState;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "SatelliteStatusDTO{" +
                "latestContibutors=" + latestContibutors +
                ", lastReceived='" + lastReceived + '\'' +
                ", latestSequenceNumber=" + latestSequenceNumber +
                ", packetCount=" + packetCount +
                ", modeSwitching='" + modeSwitching + '\'' +
                ", transponderState='" + transponderState + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
