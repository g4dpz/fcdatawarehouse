package com.badgersoft.datawarehouse.jy1sat.dto;

import java.io.Serializable;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class ImtqDTO implements Serializable {

    private long imtqMode;
    private long imtqErrorCode;
    private String imtqConfigSet;
    private long imtqMcuTemp;

    public ImtqDTO() {}

    public ImtqDTO(long imtqMode, long imtqErrorCode, String imtqConfigSet, long imtqMcuTemp) {
        this.imtqMode = imtqMode;
        this.imtqErrorCode = imtqErrorCode;
        this.imtqConfigSet = imtqConfigSet;
        this.imtqMcuTemp = imtqMcuTemp;
    }

    public long getImtqMode() {
        return imtqMode;
    }

    public long getImtqErrorCode() {
        return imtqErrorCode;
    }

    public String getImtqConfigSet() {
        return imtqConfigSet;
    }

    public long getImtqMcuTemp() {
        return imtqMcuTemp;
    }
}
