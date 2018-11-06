package com.badgersoft.datawarehouse.funcube.dto;

import java.io.Serializable;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class EpsDTO implements Serializable {

    private long panelVolts1;
    private long panelVolts2;
    private long panelVolts3;
    private long totPhotoCurr;
    private long batteryVolts;
    private long totSystemCurr;
    private long rebootCount;
    private long epsSwErrors;
    private long boostTemp1;
    private long boostTemp2;
    private long boostTemp3;
    private long batteryTemp;
    private long latchUpCount5v;
    private long latchUpCount3v3;
    private long resetCause;
    private long pptMode;

    public EpsDTO() {
    }

    public EpsDTO(long panelVolts1, long panelVolts2, long panelVolts3, long totPhotoCurr, long batteryVolts, long totSystemCurr, long rebootCount, long epsSwErrors, long boostTemp1, long boostTemp2, long boostTemp3, long batteryTemp, long latchUpCount5v, long latchUpCount3v3, long resetCause, long pptMode) {
        this.panelVolts1 = panelVolts1;
        this.panelVolts2 = panelVolts2;
        this.panelVolts3 = panelVolts3;
        this.totPhotoCurr = totPhotoCurr;
        this.batteryVolts = batteryVolts;
        this.totSystemCurr = totSystemCurr;
        this.rebootCount = rebootCount;
        this.epsSwErrors = epsSwErrors;
        this.boostTemp1 = boostTemp1;
        this.boostTemp2 = boostTemp2;
        this.boostTemp3 = boostTemp3;
        this.batteryTemp = batteryTemp;
        this.latchUpCount5v = latchUpCount5v;
        this.latchUpCount3v3 = latchUpCount3v3;
        this.resetCause = resetCause;
        this.pptMode = pptMode;
    }

    public long getPanelVolts1() {
        return panelVolts1;
    }

    public void setPanelVolts1(long panelVolts1) {
        this.panelVolts1 = panelVolts1;
    }

    public long getPanelVolts2() {
        return panelVolts2;
    }

    public void setPanelVolts2(long panelVolts2) {
        this.panelVolts2 = panelVolts2;
    }

    public long getPanelVolts3() {
        return panelVolts3;
    }

    public void setPanelVolts3(long panelVolts3) {
        this.panelVolts3 = panelVolts3;
    }

    public long getTotPhotoCurr() {
        return totPhotoCurr;
    }

    public void setTotPhotoCurr(long totPhotoCurr) {
        this.totPhotoCurr = totPhotoCurr;
    }

    public long getBatteryVolts() {
        return batteryVolts;
    }

    public void setBatteryVolts(long batteryVolts) {
        this.batteryVolts = batteryVolts;
    }

    public long getTotSystemCurr() {
        return totSystemCurr;
    }

    public void setTotSystemCurr(long totSystemCurr) {
        this.totSystemCurr = totSystemCurr;
    }

    public long getRebootCount() {
        return rebootCount;
    }

    public void setRebootCount(long rebootCount) {
        this.rebootCount = rebootCount;
    }

    public long getEpsSwErrors() {
        return epsSwErrors;
    }

    public void setEpsSwErrors(long epsSwErrors) {
        this.epsSwErrors = epsSwErrors;
    }

    public long getBoostTemp1() {
        return boostTemp1;
    }

    public void setBoostTemp1(long boostTemp1) {
        this.boostTemp1 = boostTemp1;
    }

    public long getBoostTemp2() {
        return boostTemp2;
    }

    public void setBoostTemp2(long boostTemp2) {
        this.boostTemp2 = boostTemp2;
    }

    public long getBoostTemp3() {
        return boostTemp3;
    }

    public void setBoostTemp3(long boostTemp3) {
        this.boostTemp3 = boostTemp3;
    }

    public long getBatteryTemp() {
        return batteryTemp;
    }

    public void setBatteryTemp(long batteryTemp) {
        this.batteryTemp = batteryTemp;
    }

    public long getLatchUpCount5v() {
        return latchUpCount5v;
    }

    public void setLatchUpCount5v(long latchUpCount5v) {
        this.latchUpCount5v = latchUpCount5v;
    }

    public long getLatchUpCount3v3() {
        return latchUpCount3v3;
    }

    public void setLatchUpCount3v3(long latchUpCount3v3) {
        this.latchUpCount3v3 = latchUpCount3v3;
    }

    public long getResetCause() {
        return resetCause;
    }

    public void setResetCause(long resetCause) {
        this.resetCause = resetCause;
    }

    public long getPptMode() {
        return pptMode;
    }

    public void setPptMode(long pptMode) {
        this.pptMode = pptMode;
    }
}
