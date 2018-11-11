package com.badgersoft.datawarehouse.funcube.dto;

import java.io.Serializable;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class AsibDTO implements Serializable {

    private String sunSensorX;
    private String sunSensorY;
    private String sunSensorZ;
    private String solXPlus;
    private String solXMinus;
    private String solYPlus;
    private String solYMinus;
    private String busVolts3v3;
    private String busCurr3v3;
    private String busVolts5;

    public AsibDTO() {
    }

    public AsibDTO(String sunSensorX, String sunSensorY, String sunSensorZ, String solXPlus, String solXMinus, String solYPlus, String solYMinus, String busVolts3v3, String busCurr3v3, String busVolts5) {
        this.sunSensorX = sunSensorX;
        this.sunSensorY = sunSensorY;
        this.sunSensorZ = sunSensorZ;
        this.solXPlus = solXPlus;
        this.solXMinus = solXMinus;
        this.solYPlus = solYPlus;
        this.solYMinus = solYMinus;
        this.busVolts3v3 = busVolts3v3;
        this.busCurr3v3 = busCurr3v3;
        this.busVolts5 = busVolts5;
    }

    public String getSunSensorX() {
        return sunSensorX;
    }

    public void setSunSensorX(String sunSensorX) {
        this.sunSensorX = sunSensorX;
    }

    public String getSunSensorY() {
        return sunSensorY;
    }

    public void setSunSensorY(String sunSensorY) {
        this.sunSensorY = sunSensorY;
    }

    public String getSunSensorZ() {
        return sunSensorZ;
    }

    public void setSunSensorZ(String sunSensorZ) {
        this.sunSensorZ = sunSensorZ;
    }

    public String getSolXPlus() {
        return solXPlus;
    }

    public void setSolXPlus(String solXPlus) {
        this.solXPlus = solXPlus;
    }

    public String getSolXMinus() {
        return solXMinus;
    }

    public void setSolXMinus(String solXMinus) {
        this.solXMinus = solXMinus;
    }

    public String getSolYPlus() {
        return solYPlus;
    }

    public void setSolYPlus(String solYPlus) {
        this.solYPlus = solYPlus;
    }

    public String getSolYMinus() {
        return solYMinus;
    }

    public void setSolYMinus(String solYMinus) {
        this.solYMinus = solYMinus;
    }

    public String getBusVolts3v3() {
        return busVolts3v3;
    }

    public void setBusVolts3v3(String busVolts3v3) {
        this.busVolts3v3 = busVolts3v3;
    }

    public String getBusCurr3v3() {
        return busCurr3v3;
    }

    public void setBusCurr3v3(String busCurr3v3) {
        this.busCurr3v3 = busCurr3v3;
    }

    public String getBusVolts5() {
        return busVolts5;
    }

    public void setBusVolts5(String busVolts5) {
        this.busVolts5 = busVolts5;
    }
}


