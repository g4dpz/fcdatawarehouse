package com.badgersoft.datawarehouse.eseo.dto;

import java.io.Serializable;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class PaDTO implements Serializable {

    
    private String txRevPwr;
    private String txFwdPwr;
    private String fmTxTemp;
    private String bpskTemp;
    private String bpskCurr;
    private String bpsk3v3;

    public PaDTO() {}

    public PaDTO(String txRevPwr, String txFwdPwr, String fmTxTemp, String bpskTemp, String bpskCurr, String bpsk3v3) {
        this.fmTxTemp = fmTxTemp;
        this.bpskTemp = bpskTemp;
        this.txRevPwr = txRevPwr;
        this.txFwdPwr = txFwdPwr;
        this.bpskCurr = bpskCurr;
        this.bpsk3v3 = bpsk3v3;
    }

    public String getFmTxTemp() {
        return fmTxTemp;
    }

    public String getBpskTemp() {
        return bpskTemp;
    }

    public String getTxRevPwr() {
        return txRevPwr;
    }

    public String getTxFwdPwr() {
        return txFwdPwr;
    }

    public String getBpskCurr() {
        return bpskCurr;
    }

    public String getBpsk3v3() {
        return bpsk3v3;
    }
}
