package com.badgersoft.datawarehouse.jy1sat.dto;

import com.badgersoft.datawarehouse.jy1sat.domain.HighResolutionEntity;

import java.io.Serializable;

/**
 * Created by davidjohnson on 06/11/2016.
 */
public class HighResInstantDTO implements Serializable {

    private long sequenceNumber;
    private String satelliteTime;
    private double sunSensorXPlus;
    private double sunSensorXMinus;
    private double sunSensorYPlus;
    private double sunSensorYMinus;
    private double sunSensorZPlus;
    private double sunSensorZMinus;
    private boolean coilsActive;
    private boolean xMeasValid;
    private double xCalibMtm;
    private boolean measStartError;
    private boolean yMeasValid;
    private double yCalibMtm;
    private boolean measRetreiveError;
    private boolean zMeasValid;
    private double zCalibMtm;

    public HighResInstantDTO() {}

    public HighResInstantDTO(HighResolutionEntity highResolutionEntity) {

        this.sequenceNumber = highResolutionEntity.getSequenceNumber();
        this.satelliteTime = highResolutionEntity.getSatelliteTime().toString();
        this.sunSensorXPlus = highResolutionEntity.getC1();
        this.sunSensorXMinus = highResolutionEntity.getC2();
        this.sunSensorYPlus = highResolutionEntity.getC3();
        this.sunSensorYMinus = highResolutionEntity.getC4();
        this.sunSensorZPlus = highResolutionEntity.getC5();
        this.sunSensorZMinus = highResolutionEntity.getC6();
        this.xCalibMtm = highResolutionEntity.getC7();
        this.xMeasValid = highResolutionEntity.getC8();
        this.coilsActive = highResolutionEntity.getC9();
        this.yCalibMtm = highResolutionEntity.getC10();
        this.yMeasValid = highResolutionEntity.getC11();
        this.measStartError = highResolutionEntity.getC12();
        this.zCalibMtm = highResolutionEntity.getC13();
        this.zMeasValid = highResolutionEntity.getC14();
        this.measRetreiveError = highResolutionEntity.getC15();
        
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getSatelliteTime() {
        return satelliteTime;
    }

    public void setSatelliteTime(String satelliteTime) {
        this.satelliteTime = satelliteTime;
    }

    public double getSunSensorXPlus() {
        return sunSensorXPlus;
    }

    public void setSunSensorXPlus(double sunSensorXPlus) {
        this.sunSensorXPlus = sunSensorXPlus;
    }

    public double getSunSensorXMinus() {
        return sunSensorXMinus;
    }

    public void setSunSensorXMinus(double sunSensorXMinus) {
        this.sunSensorXMinus = sunSensorXMinus;
    }

    public double getSunSensorYPlus() {
        return sunSensorYPlus;
    }

    public void setSunSensorYPlus(double sunSensorYPlus) {
        this.sunSensorYPlus = sunSensorYPlus;
    }

    public double getSunSensorYMinus() {
        return sunSensorYMinus;
    }

    public void setSunSensorYMinus(double sunSensorYMinus) {
        this.sunSensorYMinus = sunSensorYMinus;
    }

    public double getSunSensorZPlus() {
        return sunSensorZPlus;
    }

    public void setSunSensorZPlus(double sunSensorZPlus) {
        this.sunSensorZPlus = sunSensorZPlus;
    }

    public double getSunSensorZMinus() {
        return sunSensorZMinus;
    }

    public void setSunSensorZMinus(double sunSensorZMinus) {
        this.sunSensorZMinus = sunSensorZMinus;
    }

    public boolean isCoilsActive() {
        return coilsActive;
    }

    public void setCoilsActive(boolean coilsActive) {
        this.coilsActive = coilsActive;
    }

    public boolean isxMeasValid() {
        return xMeasValid;
    }

    public void setxMeasValid(boolean xMeasValid) {
        this.xMeasValid = xMeasValid;
    }

    public double getxCalibMtm() {
        return xCalibMtm;
    }

    public void setxCalibMtm(double xCalibMtm) {
        this.xCalibMtm = xCalibMtm;
    }

    public boolean isMeasStartError() {
        return measStartError;
    }

    public void setMeasStartError(boolean measStartError) {
        this.measStartError = measStartError;
    }

    public boolean isyMeasValid() {
        return yMeasValid;
    }

    public void setyMeasValid(boolean yMeasValid) {
        this.yMeasValid = yMeasValid;
    }

    public double getyCalibMtm() {
        return yCalibMtm;
    }

    public void setyCalibMtm(double yCalibMtm) {
        this.yCalibMtm = yCalibMtm;
    }

    public boolean isMeasRetreiveError() {
        return measRetreiveError;
    }

    public void setMeasRetreiveError(boolean measRetreiveError) {
        this.measRetreiveError = measRetreiveError;
    }

    public boolean iszMeasValid() {
        return zMeasValid;
    }

    public void setzMeasValid(boolean zMeasValid) {
        this.zMeasValid = zMeasValid;
    }

    public double getzCalibMtm() {
        return zCalibMtm;
    }

    public void setzCalibMtm(double zCalibMtm) {
        this.zCalibMtm = zCalibMtm;
    }
}
