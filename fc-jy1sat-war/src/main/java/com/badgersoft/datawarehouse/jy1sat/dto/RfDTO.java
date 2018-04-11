package com.badgersoft.datawarehouse.jy1sat.dto;

import java.io.Serializable;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class RfDTO implements Serializable {

    private long rxDoppler;
    private long rxRSSI;
    private String rxTemp;
    private String rxCurr;
    private String txBusCurr3v3;
    private String txBusCurr5v;

    public RfDTO() {}

    public RfDTO(long rxDoppler, long rxRSSI, String rxTemp, String rxCurr, String txBusCurr3v3, String txBusCurr5v) {
        this.rxDoppler = rxDoppler;
        this.rxRSSI = rxRSSI;
        this.rxTemp = rxTemp;
        this.rxCurr = rxCurr;
        this.txBusCurr3v3 = txBusCurr3v3;
        this.txBusCurr5v = txBusCurr5v;
    }

    public long getRxDoppler() {
        return rxDoppler;
    }

    public long getRxRSSI() {
        return rxRSSI;
    }

    public String getRxTemp() {
        return rxTemp;
    }

    public String getRxCurr() {
        return rxCurr;
    }

    public String getTxBusCurr3v3() {
        return txBusCurr3v3;
    }

    public String getTxBusCurr5v() {
        return txBusCurr5v;
    }
}
