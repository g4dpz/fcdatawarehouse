package com.badgersoft.datawarehouse.eseo.dto;

import java.io.Serializable;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class RfDTO implements Serializable {

    private String lBandTranspRxRssi;
    private String lBandCmdRxRssi;
    private String lBandCmdRxDop;
    private String lBandCmdRxTemp;

    public RfDTO() {}

    public RfDTO(String lBandTranspRxRssi, String lBandCmdRxRssi, String lBandCmdRxDop, String lBandCmdRxTemp) {
        this.lBandTranspRxRssi = lBandTranspRxRssi;
        this.lBandCmdRxRssi = lBandCmdRxRssi;
        this.lBandCmdRxDop = lBandCmdRxDop;
        this.lBandCmdRxTemp = lBandCmdRxTemp;
    }

    public String getlBandTranspRxRssi() {
        return lBandTranspRxRssi;
    }

    public String getlBandCmdRxRssi() {
        return lBandCmdRxRssi;
    }

    public String getlBandCmdRxDop() {
        return lBandCmdRxDop;
    }

    public String getlBandCmdRxTemp() {
        return lBandCmdRxTemp;
    }
}
