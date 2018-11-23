package com.badgersoft.datawarehouse.eseo.dto;

import com.badgersoft.datawarehouse.eseo.domain.RealtimeEntity;

import java.util.ArrayList;
import java.util.List;

public class RealtimeDTO extends BaseDTO {

    private long sequenceNumber;
    private long frameType;
    private String createdDate;
    private String satelliteTime;
    private double latitude;
    private double longitude;
    private List<String> sites;
    private long packetCount;
    private String latLong;

    private EpsDTO epsDTO;
    private EseoOBCDTO eseoOBCDTO;
    private EseoStatusDTO eseoStatusDTO;
    private EseoAttitudeDTO eseoAttitudeDTO;
    private RfDTO rfDTO;
    private PaDTO paDTO;
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
            formatOneDP(entity.getC1()),
            formatOneDP(entity.getC2()),
            formatOneDP(entity.getC3()),
            formatOneDP(entity.getC4()),
            formatOneDP(entity.getC5()),
            formatOneDP(entity.getC6()),
            entity.getC7(),
            formatOneDP(entity.getC8()),
            formatOneDP(entity.getC9()),
            formatOneDP(entity.getC10()),
            formatOneDP(entity.getC11())
        );

        this.paDTO = new PaDTO(
                formatOneDP(entity.getC12()),
                formatOneDP(entity.getC13()),
                formatOneDP(entity.getC15()), // FWD REV Reversed
                formatOneDP(entity.getC14()),
                formatOneDP(entity.getC16()),
                formatOneDP(entity.getC17()));

        this.rfDTO = new RfDTO(
                entity.getC18(),
                entity.getC19(),
                entity.getC20(),
                formatOneDP(entity.getC21())
        );

        this.swDTO = new SwDTO(
                entity.getC22(),
                entity.getC23(),
                entity.getC24(),
                entity.getC25(),
                entity.getC26(),
                entity.getC27(),
                entity.getC28(),
                entity.getC29(),
                entity.getC30(),
                entity.getC31()
        );

        this.eseoOBCDTO = new EseoOBCDTO();

        this.eseoStatusDTO = new EseoStatusDTO();

        this.eseoAttitudeDTO = new EseoAttitudeDTO();

    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public long getFrameType() {
        return frameType;
    }

    public void setFrameType(long frameType) {
        this.frameType = frameType;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getSatelliteTime() {
        return satelliteTime;
    }

    public void setSatelliteTime(String satelliteTime) {
        this.satelliteTime = satelliteTime;
    }

    public EpsDTO getEpsDTO() {
        return epsDTO;
    }

    public void setEpsDTO(EpsDTO epsDTO) {
        this.epsDTO = epsDTO;
    }

    public EseoOBCDTO getEseoOBCDTO() {
        return eseoOBCDTO;
    }

    public void setEseoOBCDTO(EseoOBCDTO eseoOBCDTO) {
        this.eseoOBCDTO = eseoOBCDTO;
    }

    public EseoStatusDTO getEseoStatusDTO() {
        return eseoStatusDTO;
    }

    public void setEseoStatusDTO(EseoStatusDTO eseoStatusDTO) {
        this.eseoStatusDTO = eseoStatusDTO;
    }

    public EseoAttitudeDTO getEseoAttitudeDTO() {
        return eseoAttitudeDTO;
    }

    public void setEseoAttitudeDTO(EseoAttitudeDTO eseoAttitudeDTO) {
        this.eseoAttitudeDTO = eseoAttitudeDTO;
    }

    public RfDTO getRfDTO() {
        return rfDTO;
    }

    public void setRfDTO(RfDTO rfDTO) {
        this.rfDTO = rfDTO;
    }

    public PaDTO getPaDTO() {
        return paDTO;
    }

    public void setPaDTO(PaDTO paDTO) {
        this.paDTO = paDTO;
    }

    public SwDTO getSwDTO() {
        return swDTO;
    }

    public void setSwDTO(SwDTO swDTO) {
        this.swDTO = swDTO;
    }

    public List<String> getMinima() {
        return minima;
    }

    public void setMinima(List<String> minima) {
        this.minima = minima;
    }

    public List<String> getMaxima() {
        return maxima;
    }

    public void setMaxima(List<String> maxima) {
        this.maxima = maxima;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public List<String> getSites() {
        return sites;
    }

    public long getPacketCount() {
        return packetCount;
    }
}
