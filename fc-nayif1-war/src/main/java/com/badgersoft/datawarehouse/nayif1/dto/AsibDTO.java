package com.badgersoft.datawarehouse.nayif1.dto;

import java.io.Serializable;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class AsibDTO implements Serializable {

    private long solXPlus;
    private long solXMinus;
    private long solYPlus;
    private long solYMinus;
    private long solZPlus;
    private long solZMinus;
    private long busVolts3v3;
    private long imtqUptime;
    private long busVolts5;

    public AsibDTO() {}

    public AsibDTO(long solXPlus, long solXMinus, long solYPlus, long solYMinus, long solZPlus, long solZMinus, long busVolts3v3, long imtqUptime, long busVolts5) {
        this.solXPlus = solXPlus;
        this.solXMinus = solXMinus;
        this.solYPlus = solYPlus;
        this.solYMinus = solYMinus;
        this.solZPlus = solZPlus;
        this.solZMinus = solZMinus;
        this.busVolts3v3 = busVolts3v3;
        this.imtqUptime = imtqUptime;
        this.busVolts5 = busVolts5;
    }

    public long getSolXPlus() {
        return solXPlus;
    }

    public long getSolXMinus() {
        return solXMinus;
    }

    public long getSolYPlus() {
        return solYPlus;
    }

    public long getSolYMinus() {
        return solYMinus;
    }

    public long getSolZPlus() {
        return solZPlus;
    }

    public long getSolZMinus() {
        return solZMinus;
    }

    public long getBusVolts3v3() {
        return busVolts3v3;
    }

    public long getImtqUptime() {
        return imtqUptime;
    }

    public long getBusVolts5() {
        return busVolts5;
    }
}


