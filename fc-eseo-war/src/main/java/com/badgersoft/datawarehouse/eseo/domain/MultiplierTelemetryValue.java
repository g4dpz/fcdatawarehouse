package com.badgersoft.datawarehouse.eseo.domain;

public class MultiplierTelemetryValue {

    private double multiplier;
    private long value;

    public MultiplierTelemetryValue(double multiplier, long value) {
        this.multiplier = multiplier;
        this.value = value;
    }

    protected double calculate() {
        return multiplier * value;
    }
}
