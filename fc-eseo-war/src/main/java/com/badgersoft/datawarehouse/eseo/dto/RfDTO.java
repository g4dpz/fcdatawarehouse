package com.badgersoft.datawarehouse.eseo.dto;

import java.io.Serializable;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class RfDTO implements Serializable {

    private long lBandTranspRxRssi;
    private long lBandCmdRxRssi;
    private long lBandCmdRxDop;
    private String lBandCmdRxTemp;

    public RfDTO() {}

    public RfDTO(long lBandTranspRxRssi, long lBandCmdRxRssi, long lBandCmdRxDop, String lBandCmdRxTemp) {
        this.lBandTranspRxRssi = lBandTranspRxRssi;
        this.lBandCmdRxRssi = lBandCmdRxRssi;
        this.lBandCmdRxDop = lBandCmdRxDop;
        this.lBandCmdRxTemp = lBandCmdRxTemp;
    }

    public long getlBandTranspRxRssi() {
        return lBandTranspRxRssi;
    }

    public long getlBandCmdRxRssi() {
        return lBandCmdRxRssi;
    }

    public long getlBandCmdRxDop() {
        return lBandCmdRxDop;
    }

    public String getlBandCmdRxTemp() {
        return lBandCmdRxTemp;
    }
}
