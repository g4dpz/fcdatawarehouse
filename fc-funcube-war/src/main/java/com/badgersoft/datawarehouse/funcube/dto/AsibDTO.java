package com.badgersoft.datawarehouse.funcube.dto;

import java.io.Serializable;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class AsibDTO implements Serializable {

    private double sunSensorX;
    private double sunSensorY;
    private double sunSensorZ;
    private double solXPlus;
    private double solXMinus;
    private double solYPlus;
    private double solYMinus;
    private double busVolts3v3;
    private double busCurr3v3;
    private double busVolts5;

    public AsibDTO() {
    }

    public AsibDTO(double sunSensorX, double sunSensorY, double sunSensorZ, double solXPlus, double solXMinus, double solYPlus, double solYMinus, double busVolts3v3, double busCurr3v3, double busVolts5) {
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

    public double getSunSensorX() {
        return sunSensorX;
    }

    public void setSunSensorX(double sunSensorX) {
        this.sunSensorX = sunSensorX;
    }

    public double getSunSensorY() {
        return sunSensorY;
    }

    public void setSunSensorY(double sunSensorY) {
        this.sunSensorY = sunSensorY;
    }

    public double getSunSensorZ() {
        return sunSensorZ;
    }

    public void setSunSensorZ(double sunSensorZ) {
        this.sunSensorZ = sunSensorZ;
    }

    public double getSolXPlus() {
        return solXPlus;
    }

    public void setSolXPlus(double solXPlus) {
        this.solXPlus = solXPlus;
    }

    public double getSolXMinus() {
        return solXMinus;
    }

    public void setSolXMinus(double solXMinus) {
        this.solXMinus = solXMinus;
    }

    public double getSolYPlus() {
        return solYPlus;
    }

    public void setSolYPlus(double solYPlus) {
        this.solYPlus = solYPlus;
    }

    public double getSolYMinus() {
        return solYMinus;
    }

    public void setSolYMinus(double solYMinus) {
        this.solYMinus = solYMinus;
    }

    public double getBusVolts3v3() {
        return busVolts3v3;
    }

    public void setBusVolts3v3(double busVolts3v3) {
        this.busVolts3v3 = busVolts3v3;
    }

    public double getBusCurr3v3() {
        return busCurr3v3;
    }

    public void setBusCurr3v3(double busCurr3v3) {
        this.busCurr3v3 = busCurr3v3;
    }

    public double getBusVolts5() {
        return busVolts5;
    }

    public void setBusVolts5(double busVolts5) {
        this.busVolts5 = busVolts5;
    }
}


