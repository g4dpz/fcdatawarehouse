package com.badgersoft.datawarehouse.eseo.domain;

public class PolynomialTelemetryValue {

    private double a;
    private double b;
    private double c;
    private double d;
    private double e;
    private double value;

    public PolynomialTelemetryValue(double e, double d, double c, double b, double a, Long value) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.value = value.doubleValue();
    }

    public double calculate() {
        return e * Math.pow(value, 4.0) + d * Math.pow(value, 3.0) + c * Math.pow(value, 2.0) + b * value + a;
    }
}
