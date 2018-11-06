package com.badgersoft.datawarehouse.funcube.dto;

import java.io.Serializable;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class PaDTO implements Serializable {

    
    private double txRevPwr;
    private double txFwdPwr;
    private double txTemp;
    private double txCurr;

    public PaDTO() {}

    public PaDTO(double txRevPwr, double txFwdPwr, double txTemp, double txCurr) {
        this.txTemp = txTemp;
        this.txCurr = txCurr;
        this.txRevPwr = txRevPwr;
        this.txFwdPwr = txFwdPwr;
    }

    public double getTxTemp() {
        return txTemp;
    }

    public double getTxCurr() {
        return txCurr;
    }

    public double getTxRevPwr() {
        return txRevPwr;
    }

    public double getTxFwdPwr() {
        return txFwdPwr;
    }
}
