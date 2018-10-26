package com.badgersoft.datawarehouse.nayif1.dto;


import com.badgersoft.datawarehouse.nayif1.domain.WholeOrbitDataEntity;

import java.io.Serializable;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class WodInstantDTO implements Serializable {

    private long sequenceNumber;

    private String satelliteTime;

    private double cctTemp;
    private double rfXtalTemp;
    private double paTemp;

    private double solXPlusTemp;
    private double solXMinusTemp;
    private double solYPlusTemp;
    private double solYMinusTemp;
    private double solZPlusTemp;
    private double solZMinusTemp;
    private long sunSensorXPlus;
    private long sunSensorXMinus;
    private long sunSensorYPlus;
    private long sunSensorYMinus;
    private long sunSensorZPlus;
    private long sunSensorZMinus;
    private long battTemp;
    private long totPhotoCurr;
    private long battVolts;
    private long totSystemCurr;

    public WodInstantDTO() {}

    public WodInstantDTO(WholeOrbitDataEntity wholeOrbitDataEntity) {
        sequenceNumber = wholeOrbitDataEntity.getSequenceNumber();
        satelliteTime = wholeOrbitDataEntity.getSatelliteTime().toString();
        cctTemp = wholeOrbitDataEntity.getC1();
        rfXtalTemp = wholeOrbitDataEntity.getC2();
        paTemp = wholeOrbitDataEntity.getC3();
        solXPlusTemp = wholeOrbitDataEntity.getC4();
        solXMinusTemp = wholeOrbitDataEntity.getC5();
        solYPlusTemp = wholeOrbitDataEntity.getC6();
        solYMinusTemp = wholeOrbitDataEntity.getC7();
        solZPlusTemp = wholeOrbitDataEntity.getC8();
        solZMinusTemp = wholeOrbitDataEntity.getC9();
        sunSensorXPlus = wholeOrbitDataEntity.getC10();
        sunSensorXMinus = wholeOrbitDataEntity.getC11();
        sunSensorYPlus = wholeOrbitDataEntity.getC12();
        sunSensorYMinus = wholeOrbitDataEntity.getC13();
        sunSensorZPlus = wholeOrbitDataEntity.getC14();
        sunSensorZMinus = wholeOrbitDataEntity.getC15();
        battTemp = wholeOrbitDataEntity.getC16();
        totPhotoCurr = wholeOrbitDataEntity.getC17();
        battVolts = wholeOrbitDataEntity.getC18();
        totSystemCurr = wholeOrbitDataEntity.getC19();
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public String getSatelliteTime() {
        return satelliteTime;
    }

    public double getCctTemp() {
        return cctTemp;
    }

    public double getRfXtalTemp() {
        return rfXtalTemp;
    }

    public double getPaTemp() {
        return paTemp;
    }

    public double getSolXPlusTemp() {
        return solXPlusTemp;
    }

    public double getSolXMinusTemp() {
        return solXMinusTemp;
    }

    public double getSolYPlusTemp() {
        return solYPlusTemp;
    }

    public double getSolYMinusTemp() {
        return solYMinusTemp;
    }

    public double getSolZPlusTemp() {
        return solZPlusTemp;
    }

    public double getSolZMinusTemp() {
        return solZMinusTemp;
    }

    public long getSunSensorXPlus() {
        return sunSensorXPlus;
    }

    public long getSunSensorXMinus() {
        return sunSensorXMinus;
    }

    public long getSunSensorYPlus() {
        return sunSensorYPlus;
    }

    public long getSunSensorYMinus() {
        return sunSensorYMinus;
    }

    public long getSunSensorZPlus() {
        return sunSensorZPlus;
    }

    public long getSunSensorZMinus() {
        return sunSensorZMinus;
    }

    public long getBattTemp() {
        return battTemp;
    }

    public long getTotPhotoCurr() {
        return totPhotoCurr;
    }

    public long getBattVolts() {
        return battVolts;
    }

    public long getTotSystemCurr() {
        return totSystemCurr;
    }
}
