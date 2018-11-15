package com.badgersoft.datawarehouse.eseo.dto;

import java.io.Serializable;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class EpsDTO implements Serializable {

    private String dcConvVoltsOut;
    private String dcConvCurrOut;
    private String dcConvTemp;
    private String enclosureTemp;
    private String cctProcTemp;
    private String volts3v3;

    private long curr3v3;
    private String volts6v9;
    private String curr6v9;
    private String volts9v;
    private String curr9v;

    public EpsDTO() {}

    public EpsDTO(String dcConvVoltsOut, String dcConvCurrOut, String dcConvTemp, String enclosureTemp, String cctProcTemp, String volts3v3, long curr3v3, String volts6v9, String curr6v9, String volts9v, String curr9v) {
        this.dcConvVoltsOut = dcConvVoltsOut;
        this.dcConvCurrOut = dcConvCurrOut;
        this.dcConvTemp = dcConvTemp;
        this.enclosureTemp = enclosureTemp;
        this.cctProcTemp = cctProcTemp;
        this.volts3v3 = volts3v3;
        this.curr3v3 = curr3v3;
        this.volts6v9 = volts6v9;
        this.curr6v9 = curr6v9;
        this.volts9v = volts9v;
        this.curr9v = curr9v;
    }

    public String getDcConvVoltsOut() {
        return dcConvVoltsOut;
    }

    public String getDcConvCurrOut() {
        return dcConvCurrOut;
    }

    public String getDcConvTemp() {
        return dcConvTemp;
    }

    public String getEnclosureTemp() {
        return enclosureTemp;
    }

    public String getCctProcTemp() {
        return cctProcTemp;
    }

    public String getVolts3v3() {
        return volts3v3;
    }

    public long getCurr3v3() {
        return curr3v3;
    }

    public String getVolts6v9() {
        return volts6v9;
    }

    public String getCurr6v9() {
        return curr6v9;
    }

    public String getVolts9v() {
        return volts9v;
    }

    public String getCurr9v() {
        return curr9v;
    }
}
