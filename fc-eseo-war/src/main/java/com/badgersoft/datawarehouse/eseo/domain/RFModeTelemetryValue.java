package com.badgersoft.datawarehouse.eseo.domain;

public class RFModeTelemetryValue {

    private final long value;

    public RFModeTelemetryValue(long value) {
        this.value = value;
    }

    public String getValueAsString() {
        switch ((int) value)
        {
            case 0: return "Receive only, with data collection (0)";
            case 1: return "Low power BPSK telemetry mode (1)";
            case 2: return "High power BPSK telemetry mode (2)";
            case 3: return "Low power transponder mode (3)";
            case 4: return "High power transponder mode (4)";
            case 5: return "Autonomous mode (5)";
            default:
                return "Unknown (" + value + ")";
        }
    }
}
