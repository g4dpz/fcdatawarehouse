package com.badgersoft.datawarehouse.eseo.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class SwDTO implements Serializable {
    private static Logger LOG = LoggerFactory.getLogger(SwDTO.class.getName());

    private long sequenceNumber;
    private long dtmfLastCmd;
    private String rfMode;
    private String dataMode;
    private String payloadStatus;
    private String inEclipseMode;
    private String inAutoMode;
    private String ctcssDetect;
    private boolean safeModeState;
    private String inSafeMode;

    public SwDTO() {}

    public SwDTO(long sequenceNumber, long dtmfLastCmd, String rfMode, String dataMode, String payloadStatus, String inEclipseMode, String inAutoMode, String ctcssDetect, boolean safeModeState, String inSafeMode) {
        this.sequenceNumber = sequenceNumber;
        this.dtmfLastCmd = dtmfLastCmd;
        this.rfMode = rfMode;
        this.dataMode = dataMode;
        this.payloadStatus = payloadStatus;
        this.inEclipseMode = inEclipseMode;
        this.inAutoMode = inAutoMode;
        this.ctcssDetect = ctcssDetect;
        this.safeModeState = safeModeState;
        this.inSafeMode = inSafeMode;
    }

    public long getDtmfLastCmd() {
        return dtmfLastCmd;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getRfMode() {
        return rfMode;
    }

    public void setRfMode(String rfMode) {
        this.rfMode = rfMode;
    }

    public String getDataMode() {
        return dataMode;
    }

    public void setDataMode(String dataMode) {
        this.dataMode = dataMode;
    }

    public String getPayloadStatus() {
        return payloadStatus;
    }

    public void setPayloadStatus(String payloadStatus) {
        this.payloadStatus = payloadStatus;
    }

    public String getInEclipseMode() {
        return inEclipseMode;
    }

    public void setInEclipseMode(String inEclipseMode) {
        this.inEclipseMode = inEclipseMode;
    }

    public String getInAutoMode() {
        return inAutoMode;
    }

    public void setInAutoMode(String inAutoMode) {
        this.inAutoMode = inAutoMode;
    }

    public String getCtcssDetect() {
        return ctcssDetect;
    }

    public void setCtcssDetect(String ctcssDetect) {
        this.ctcssDetect = ctcssDetect;
    }

    public boolean getSafeModeState() {
        return safeModeState;
    }

    public void setSafeModeState(boolean safeModeState) {
        this.safeModeState = safeModeState;
    }

    public String getInSafeMode() {
        return inSafeMode;
    }

    public void setInSafeMode(String inSafeMode) {
        this.inSafeMode = inSafeMode;
    }
}
