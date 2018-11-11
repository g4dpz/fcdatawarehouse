package com.badgersoft.datawarehouse.funcube.dto;

import java.io.Serializable;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class PaDTO implements Serializable {

    
    private String txRevPwr;
    private String txFwdPwr;
    private String txTemp;
    private String txCurr;

    public PaDTO() {}

    public PaDTO(String txRevPwr, String txFwdPwr, String txTemp, String txCurr) {
        this.txTemp = txTemp;
        this.txCurr = txCurr;
        this.txRevPwr = txRevPwr;
        this.txFwdPwr = txFwdPwr;
    }

    public String getTxTemp() {
        return txTemp;
    }

    public String getTxCurr() {
        return txCurr;
    }

    public String getTxRevPwr() {
        return txRevPwr;
    }

    public String getTxFwdPwr() {
        return txFwdPwr;
    }
}
