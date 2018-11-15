package com.badgersoft.datawarehouse.eseo.domain;

public class MultiplierOffsetTelemetryValue {

    private double multiplier;
    private double offset;
    private long value;

    public MultiplierOffsetTelemetryValue(double multiplier, double offset, long value) {
        this.multiplier = multiplier;
        this.offset = offset;
        this.value = value;
    }

    protected double calculate() {
        return (multiplier * value) + offset;
    }
}
