package com.badgersoft.datawarehouse.jy1sat.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by davidjohnson on 25/09/2016.
 */
@Entity
@Table(name = "whole_orbit_data")
public class WholeOrbitDataEntity extends TelemetryEntity {

    static {
        setupSunSensors();
        PA_TEMPS_256 = buildLookupTable(256);
        PA_TEMPS_1024 = buildLookupTable(1024);
    }


    @Id
    private long id;

    @Column(name = "sequence_number")
    private long sequenceNumber;

    @Column(name = "satellite_time")
    private Date satelliteTime;


    private double c1;
    private double c2;
    private double c3;
    private double c4;
    private double c5;
    private double c6;
    private double c7;
    private double c8;
    private double c9;
    private long c10;
    private long c11;
    private long c12;
    private long c13;
    private long c14;
    private long c15;
    private long c16;
    private long c17;
    private long c18;
    private long c19;
    private long badger;

    public WholeOrbitDataEntity() {}

    public WholeOrbitDataEntity(long id, Long sequenceNumber, Date satelliteTime, String binaryString) {
        this.id = id;
        this.sequenceNumber = sequenceNumber;
        this.satelliteTime = satelliteTime;

        // Temp @ CCT microcontroller
        c1 = calcMcuTemp(getBitsAsULong(12, binaryString));
        // Temp @ RF board crystal
        c2 = ((getBitsAsULong(12, binaryString) & 1023) * -0.3465) + 266.70646;
        // Temp @ Power amplifier
        badger = getBitsAsULong(12, binaryString);
        c3 = PA_TEMPS_1024[(int) (badger & 1023)];
        // Solar panel temp +X
        c4 = scale(getBitsAsULong(10, binaryString), -0.2080, 158.792);
        // Solar panel temp –X
        c5 = scale(getBitsAsULong(10, binaryString), -0.2080, 158.792);
        // Solar panel temp +Y
        c6 = scale(getBitsAsULong(10, binaryString), -0.2080, 158.792);
        // Solar panel temp -Y
        c7 = scale(getBitsAsULong(10, binaryString), -0.2080, 158.792);
        // Solar panel temp +Z
        c8 = scale(getBitsAsULong(10, binaryString), -0.2080, 158.792);
        // Solar panel temp -Z
        c9 = scale(getBitsAsULong(10, binaryString), -0.2080, 158.792);
        // Sun Sensor X+
        c10 = getBitsAsULong(10, binaryString);
        // Sun Sensor X-
        c11 = getBitsAsULong(10, binaryString);
        // Sun Sensor Y+
        c12 = getBitsAsULong(10, binaryString);
        // Sun Sensor Y-
        c13 = getBitsAsULong(10, binaryString);
        // Sun Sensor Z+
        c14 = getBitsAsULong(10, binaryString);
        // Sun Sensor Z-
        c15 = getBitsAsULong(10, binaryString);
        // Battery temp
        c16 = twosComplement(getBitsAsULong(8, binaryString));
        // Total Photo current
        c17 = getBitsAsULong(10, binaryString);
        // Battery voltage
        c18 = getBitsAsULong(14, binaryString);
        // Total system current
        c19 = getBitsAsULong(12, binaryString);

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Date getSatelliteTime() {
        return satelliteTime;
    }

    public void setSatelliteTime(Date satelliteTime) {
        this.satelliteTime = satelliteTime;
    }

    public double getC1() {
        return c1;
    }

    public void setC1(double c1) {
        this.c1 = c1;
    }

    public double getC2() {
        return c2;
    }

    public void setC2(double c2) {
        this.c2 = c2;
    }

    public double getC3() {
        return c3;
    }

    public void setC3(double c3) {
        this.c3 = c3;
    }

    public double getC4() {
        return c4;
    }

    public void setC4(double c4) {
        this.c4 = c4;
    }

    public double getC5() {
        return c5;
    }

    public void setC5(double c5) {
        this.c5 = c5;
    }

    public double getC6() {
        return c6;
    }

    public void setC6(double c6) {
        this.c6 = c6;
    }

    public double getC7() {
        return c7;
    }

    public void setC7(double c7) {
        this.c7 = c7;
    }

    public double getC8() {
        return c8;
    }

    public void setC8(double c8) {
        this.c8 = c8;
    }

    public double getC9() {
        return c9;
    }

    public void setC9(double c9) {
        this.c9 = c9;
    }

    public long getC10() {
        return c10;
    }

    public void setC10(long c10) {
        this.c10 = c10;
    }

    public long getC11() {
        return c11;
    }

    public void setC11(long c11) {
        this.c11 = c11;
    }

    public long getC12() {
        return c12;
    }

    public void setC12(long c12) {
        this.c12 = c12;
    }

    public long getC13() {
        return c13;
    }

    public void setC13(long c13) {
        this.c13 = c13;
    }

    public long getC14() {
        return c14;
    }

    public void setC14(long c14) {
        this.c14 = c14;
    }

    public long getC15() {
        return c15;
    }

    public void setC15(long c15) {
        this.c15 = c15;
    }

    public long getC16() {
        return c16;
    }

    public void setC16(long c16) {
        this.c16 = c16;
    }

    public long getC17() {
        return c17;
    }

    public void setC17(long c17) {
        this.c17 = c17;
    }

    public long getC18() {
        return c18;
    }

    public void setC18(long c18) {
        this.c18 = c18;
    }

    public long getC19() {
        return c19;
    }

    public void setC19(long c19) {
        this.c19 = c19;
    }

    public long getBadger() {
        return badger;
    }

    public void setBadger(long badger) {
        this.badger = badger;
    }

    @Transient
    private double calcMcuTemp(long adc)
    {
        double vt = adc * 0.00078753; //Convert the ADC reading into voltage

        if (vt >= 0.7012) // Check for Hot or Cold Slope
        {
            return 25.0 - ((vt - 0.7012) / 0.001646); // Cold Slope)
        }
        else
        {
            return 25.0 - ((vt - 0.7012) / 0.001749); // Hot Slope
        }
    }

}
