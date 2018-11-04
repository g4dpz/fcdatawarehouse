package com.badgersoft.datawarehouse.eseo.domain;

public class SatDataModeTelemetryValue {

    private final long value;

    public SatDataModeTelemetryValue(long value) {
        this.value = value;
    }

    public String getValueAsString() {

        switch ((int) value)
        {
            case 0: return "AMS + ESEO data at 1k2 (0)";
            case 1: return "AMS + ESEO data + Payload 4k8 (1)";
            default:
                return "Unknown (" + value + ")";
        }
    }
}
