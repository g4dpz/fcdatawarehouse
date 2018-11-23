package com.badgersoft.datawarehouse.funcube.dto;


import com.badgersoft.datawarehouse.funcube.domain.RealtimeEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by davidjohnson on 30/10/2016.
 */
public class RealtimeDTO implements Serializable {

    private String latLong;
    private long sequenceNumber;
    private long frameType;
    private String createdDate;
    private String satelliteTime;
    private double latitude;
    private double longitude;
    private List<String> sites;
    private long packetCount;

    private EpsDTO epsDTO;
    private AsibDTO asibDTO;
    private RfDTO rfDTO;
    private PaDTO paDTO;
    private AntsDTO antsDTO;
    private SwDTO swDTO;

    private List<String> minima = new ArrayList();
    private List<String> maxima = new ArrayList();

    public RealtimeDTO() {}

    public RealtimeDTO(final RealtimeEntity entity, List<Double> minima, List<Double> maxima, List<String> sites, long packetCount) {
        this.sequenceNumber = entity.getSequenceNumber();
        this.frameType = entity.getFrameType();
        this.createdDate = "Data received: " + entity.getCreatedDate().toString();
        this.satelliteTime = entity.getSatelliteTime().toString();

        this.latitude = Double.parseDouble(entity.getLatitude());
        this.longitude = Double.parseDouble(entity.getLongitude());
        this.sites = sites;
        this.packetCount = packetCount;

        String longitudeString;

        if (longitude > 180.0) {
            longitudeString = String.format("%5.1f W", 360.0 - longitude);
        } else {
            longitudeString = String.format("%5.1f E", longitude);
        }

        latLong = String.format("Satellite Latitude, Longitude: %5.1f %s, %s",
                Math.abs(latitude), (latitude < 0 ? "S" : "N"), longitudeString);

        this.epsDTO = new EpsDTO(
                entity.getC1(),
                entity.getC2(),
                entity.getC3(),
                entity.getC4(),
                entity.getC5(),
                entity.getC6(),
                entity.getC7(),
                entity.getC8(),
                entity.getC9(),
                entity.getC10(),
                entity.getC11(),
                entity.getC12(),
                entity.getC13(),
                entity.getC14(),
                entity.getC15(),
                entity.getC16()
        );

        this.asibDTO = new AsibDTO(
                formatOneDP(entity.getC17()),
                formatOneDP(entity.getC18()),
                formatOneDP(entity.getC19()),
                formatOneDP(entity.getC20()),
                formatOneDP(entity.getC21()),
                formatOneDP(entity.getC22()),
                formatOneDP(entity.getC23()),
                formatOneDP(entity.getC24()),
                formatOneDP(entity.getC25()),
                formatOneDP(entity.getC26())
        );

        this.rfDTO = new RfDTO(
                entity.getC27(),
                entity.getC28(),
                formatOneDP(entity.getC29()),
                entity.getC30(),
                entity.getC31(),
                entity.getC32()
        );

        this.paDTO = new PaDTO(
                formatOneDP(entity.getC34()), // FWD / REV Power reversed
                formatOneDP(entity.getC33()),
                formatOneDP(entity.getC35()),
                formatOneDP(entity.getC36())
        );

        this.antsDTO = new AntsDTO(
                formatOneDP(entity.getC37()),
                formatOneDP(entity.getC38()),
                entity.getC39() ? "Deployed" : "Undeployed",
                entity.getC40() ? "Deployed" : "Undeployed",
                entity.getC41() ? "Deployed" : "Undeployed",
                entity.getC42() ? "Deployed" : "Undeployed"
        );

        this.swDTO = new SwDTO(
            entity.getC43(),
            entity.getC44(),
            entity.getC45() ? "YES" : "NO",
            entity.getC46() ? "YES" : "NO",
            entity.getC47() ? "YES" : "NO",
            entity.getC48() ? "YES" : "NO",
            entity.getC49() ? "YES" : "NO",
            entity.getC50() ? "YES" : "NO",
            entity.getC51() ? "YES" : "NO",
            entity.getC52() ? "YES" : "NO",
            entity.getC53() ? "YES" : "NO",
            entity.getC54() ? "YES" : "NO",
            entity.getC55() ? "ON" : "Off",
            entity.getC56() ? "ON" : "Off",
            entity.getC57() ? "YES" : "NO"
        );

        this.maxima = reformat(maxima);
        this.minima = reformat(minima);
    }

    public RealtimeDTO(RealtimeEntity realtimeEntity) {
        this(realtimeEntity, Collections.EMPTY_LIST, Collections.EMPTY_LIST, Collections.EMPTY_LIST, 0L);
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }
    public long getFrameType() {
        return frameType;
    }
    public String getCreatedDate() {
        return createdDate;
    }
    public String getSatelliteTime() {
        return satelliteTime;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }

    public EpsDTO getEpsDTO() {
        return epsDTO;
    }

    public AsibDTO getAsibDTO() {
        return asibDTO;
    }

    public RfDTO getRfDTO() {
        return rfDTO;
    }

    public PaDTO getPaDTO() {
        return paDTO;
    }

    public AntsDTO getAntsDTO() {
        return antsDTO;
    }

    public SwDTO getSwDTO() {
        return swDTO;
    }

    public List<String> getMinima() {
        return minima;
    }

    public List<String> getMaxima() {
        return maxima;
    }

    private String formatOneDP(double value) {
        return String.format("%10.1f", value).trim();
    }

    private String formatNoDP(double value) {
        return String.format("%10.0f", value).trim();
    }

    public String getLatLong() {
        return latLong;
    }

    public List<String> getSites() {
        return sites;
    }

    public long getPacketCount() {
        return packetCount;
    }

    private List<String> reformat(List<Double> values) {
        List<String> formatted = new ArrayList(values.size());
        int i = 0;
        for (Double value : values) {
            formatted.add(i++, formatNoDP(value.doubleValue()));
        }
        return formatted;
    }
}
