package com.badgersoft.datawarehouse.jy1sat.dto;


import com.badgersoft.datawarehouse.jy1sat.domain.RealtimeEntity;

import java.io.Serializable;
import java.util.ArrayList;
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
    private ImtqDTO imtqDTO;
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
                entity.getC16(),
                entity.getC17(),
                entity.getC18()
        );
        
        this.imtqDTO = new ImtqDTO(
                entity.getC19(),
                entity.getC20(),
                entity.getC21() ? "ON" : "OFF",
                entity.getC22()
        );

        this.asibDTO = new AsibDTO(
                (long) entity.getC23(),
                (long) entity.getC24(),
                (long) entity.getC25(),
                (long) entity.getC26(),
                (long) entity.getC27(),
                (long) entity.getC28(),
                (long) entity.getC29(),
                entity.getC30(),
                (long) entity.getC31()
        );
        
        this.rfDTO = new RfDTO(
                entity.getC32(),
                entity.getC33(),
                formatOneDP(entity.getC34()),
                formatOneDP(entity.getC35()),
                formatOneDP(entity.getC36()),
                formatOneDP(entity.getC37())
        );

        this.paDTO = new PaDTO(
            formatOneDP(entity.getC39()), // FWD, REV Power Reversed
            formatOneDP(entity.getC38()),
            formatOneDP(entity.getC40()),
            formatOneDP(entity.getC41())
        );

        this.antsDTO = new AntsDTO(
            formatOneDP(entity.getC42()),
            formatOneDP(entity.getC43()),
            entity.getC44() ? "Deployed" : "Undeployed",
            entity.getC45() ? "Deployed" : "Undeployed",
            entity.getC46() ? "Deployed" : "Undeployed",
            entity.getC47() ? "Deployed" : "Undeployed"
        );

        this.swDTO = new SwDTO(
            entity.getC48(),
            entity.getC49(),
            entity.getC50() ? "OK" : "Inactive",
            entity.getC51() ? "OK" : "Inactive",
            entity.getC52() ? "OK" : "Inactive",
            entity.getC53() ? "OK" : "Inactive",
            entity.getC54() ? "OK" : "Inactive",
            entity.getC55() ? "OK" : "Inactive",
            entity.getC56() ? "OK" : "Inactive",
            entity.getC57() ? "OK" : "Inactive",
            entity.getC58() ? "ON" : "Off",
            entity.getC59() ? "ON" : "Off",
            entity.getC60() ? "ON" : "Off",
            entity.getC61() ? "ON" : "Off",
            entity.getC62() ? "ON" : "Off"
        );

        this.maxima = reformat(maxima);
        this.minima = reformat(minima);
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

    public ImtqDTO getImtqDTO() {
        return imtqDTO;
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

    public String getLatLong() {
        return latLong;
    }

    public List<String> getSites() {
        return sites;
    }

    public long getPacketCount() {
        return packetCount;
    }

    private String formatOneDP(double value) {
        return String.format("%10.1f", value).trim();
    }

    private String formatNoDP(double value) {
        return String.format("%10.0f", value).trim();
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
