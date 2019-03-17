package com.badgersoft.datawarehouse.funcube.dto;


import com.badgersoft.datawarehouse.funcube.domain.WholeOrbitDataEntity;

import java.io.Serializable;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class WodInstantDTO implements Serializable {

    private long sequenceNumber;

    private String satelliteTime;

    private double blackChassis;
    private double silverChassis;
    private double blackPanel;
    private double silverPanel;
    private double solPanelXplus;
    private double solPanelXminus;
    private double solPanelYplus;
    private double solPanelYminus;
    private double solPanelXvolts;
    private double solPanelYvolts;
    private double solPanelZvolts;
    private double totPhotoCurr;
    private double batteryVolts;
    private double totSystemCurr;

    public WodInstantDTO() {}

    public WodInstantDTO(WholeOrbitDataEntity wholeOrbitDataEntity) {
        sequenceNumber = wholeOrbitDataEntity.getSequenceNumber();
        satelliteTime = wholeOrbitDataEntity.getSatelliteTime().toString();
        blackChassis=  wholeOrbitDataEntity.getBlackChassis();
        silverChassis=  wholeOrbitDataEntity.getSilverChassis();
        blackPanel=  wholeOrbitDataEntity.getBlackPanel();
        silverPanel=  wholeOrbitDataEntity.getSilverPanel();
        solPanelXplus=  wholeOrbitDataEntity.getSolPanelXplus();
        solPanelXminus=  wholeOrbitDataEntity.getSolPanelXminus();
        solPanelYplus=  wholeOrbitDataEntity.getSolPanelYplus();
        solPanelYminus=  wholeOrbitDataEntity.getSolPanelYminus();
        solPanelXvolts=  wholeOrbitDataEntity.getSolPanelXvolts();
        solPanelYvolts=  wholeOrbitDataEntity.getSolPanelYvolts();
        solPanelZvolts=  wholeOrbitDataEntity.getSolPanelZvolts();
        totPhotoCurr=  wholeOrbitDataEntity.getTotPhotoCurr();
        batteryVolts=  wholeOrbitDataEntity.getBatteryVolts();
        totSystemCurr=  wholeOrbitDataEntity.getTotSystemCurr();
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

    public double getBlackChassis() {
        return blackChassis;
    }

    public void setBlackChassis(double blackChassis) {
        this.blackChassis = blackChassis;
    }

    public double getSilverChassis() {
        return silverChassis;
    }

    public void setSilverChassis(double silverChassis) {
        this.silverChassis = silverChassis;
    }

    public double getBlackPanel() {
        return blackPanel;
    }

    public void setBlackPanel(double blackPanel) {
        this.blackPanel = blackPanel;
    }

    public double getSilverPanel() {
        return silverPanel;
    }

    public void setSilverPanel(double silverPanel) {
        this.silverPanel = silverPanel;
    }

    public double getSolPanelXplus() {
        return solPanelXplus;
    }

    public void setSolPanelXplus(double solPanelXplus) {
        this.solPanelXplus = solPanelXplus;
    }

    public double getSolPanelXminus() {
        return solPanelXminus;
    }

    public void setSolPanelXminus(double solPanelXminus) {
        this.solPanelXminus = solPanelXminus;
    }

    public double getSolPanelYplus() {
        return solPanelYplus;
    }

    public void setSolPanelYplus(double solPanelYplus) {
        this.solPanelYplus = solPanelYplus;
    }

    public double getSolPanelYminus() {
        return solPanelYminus;
    }

    public void setSolPanelYminus(double solPanelYminus) {
        this.solPanelYminus = solPanelYminus;
    }

    public double getSolPanelXvolts() {
        return solPanelXvolts;
    }

    public void setSolPanelXvolts(double solPanelXvolts) {
        this.solPanelXvolts = solPanelXvolts;
    }

    public double getSolPanelYvolts() {
        return solPanelYvolts;
    }

    public void setSolPanelYvolts(double solPanelYvolts) {
        this.solPanelYvolts = solPanelYvolts;
    }

    public double getSolPanelZvolts() {
        return solPanelZvolts;
    }

    public void setSolPanelZvolts(double solPanelZvolts) {
        this.solPanelZvolts = solPanelZvolts;
    }

    public double getTotPhotoCurr() {
        return totPhotoCurr;
    }

    public void setTotPhotoCurr(double totPhotoCurr) {
        this.totPhotoCurr = totPhotoCurr;
    }

    public double getBatteryVolts() {
        return batteryVolts;
    }

    public void setBatteryVolts(double batteryVolts) {
        this.batteryVolts = batteryVolts;
    }

    public double getTotSystemCurr() {
        return totSystemCurr;
    }

    public void setTotSystemCurr(double totSystemCurr) {
        this.totSystemCurr = totSystemCurr;
    }
}
