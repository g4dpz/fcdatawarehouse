package com.badgersoft.datawarehouse.nayif1.dto;

import java.io.Serializable;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class EpsDTO implements Serializable {

    private long panelVolts1;
    private long panelVolts2;
    private long panelVolts3;
    private long batteryVolts;
    private long panelCurr1;

    private long panelCurr2;
    private long panelCurr3;
    private long totPhotoCurr;
    private long totSystemCurr;
    private long rebootCount;
    private long boostTemp1;
    private long boostTemp2;

    private long boostTemp3;
    private long batteryTemp;
    private long latchUpCount5v;
    private long channelCurren5V;
    private long resetCause;
    private long pptMode;

    public EpsDTO() {}

    public EpsDTO(long panelVolts1, long panelVolts2, long panelVolts3, long batteryVolts, long panelCurr1, long panelCurr2, long panelCurr3, long totPhotoCurr, long totSystemCurr, long rebootCount, long boostTemp1, long boostTemp2, long boostTemp3, long batteryTemp, long latchUpCount5v, long channelCurren5V, long resetCause, long pptMode) {
        this.panelVolts1 = panelVolts1;
        this.panelVolts2 = panelVolts2;
        this.panelVolts3 = panelVolts3;
        this.batteryVolts = batteryVolts;
        this.panelCurr1 = panelCurr1;
        this.panelCurr2 = panelCurr2;
        this.panelCurr3 = panelCurr3;
        this.totPhotoCurr = totPhotoCurr;
        this.totSystemCurr = totSystemCurr;
        this.rebootCount = rebootCount;
        this.boostTemp1 = boostTemp1;
        this.boostTemp2 = boostTemp2;
        this.boostTemp3 = boostTemp3;
        this.batteryTemp = batteryTemp;
        this.latchUpCount5v = latchUpCount5v;
        this.channelCurren5V = channelCurren5V;
        this.resetCause = resetCause;
        this.pptMode = pptMode;
    }

    public long getPanelVolts1() {
        return panelVolts1;
    }

    public long getPanelVolts2() {
        return panelVolts2;
    }

    public long getPanelVolts3() {
        return panelVolts3;
    }

    public long getBatteryVolts() {
        return batteryVolts;
    }

    public long getPanelCurr1() {
        return panelCurr1;
    }

    public long getPanelCurr2() {
        return panelCurr2;
    }

    public long getPanelCurr3() {
        return panelCurr3;
    }

    public long getTotPhotoCurr() {
        return totPhotoCurr;
    }

    public long getTotSystemCurr() {
        return totSystemCurr;
    }

    public long getRebootCount() {
        return rebootCount;
    }

    public long getBoostTemp1() {
        return boostTemp1;
    }

    public long getBoostTemp2() {
        return boostTemp2;
    }

    public long getBoostTemp3() {
        return boostTemp3;
    }

    public long getBatteryTemp() {
        return batteryTemp;
    }

    public long getLatchUpCount5v() {
        return latchUpCount5v;
    }

    public long getChannelCurren5V() {
        return channelCurren5V;
    }

    public long getResetCause() {
        return resetCause;
    }

    public long getPptMode() {
        return pptMode;
    }
}
