package com.badgersoft.datawarehouse.funcube.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by davidjohnson on 26/09/2016.
 */
@Entity
@Table(name = "high_precision")
public class HighResolutionEntity extends TelemetryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="sequence_number")
    private long sequenceNumber;


    @Column(name="satellite_time")
    private Date satelliteTime;

    private double c1;
    private double c2;
    private double c3;
    private double c4;
    private double c5;
    private double c6;
    private double c7;
    private boolean c8;
    private boolean c9;
    private double c10;
    private boolean c11;
    private boolean c12;
    private double c13;
    private boolean c14;
    private boolean c15;

    public HighResolutionEntity() {
    }

    public HighResolutionEntity(long sequenceNumber, Date satelliteTime, String binaryString) {
        setSequenceNumber(sequenceNumber);
        setSatelliteTime(satelliteTime);
        setC1(getBitsAsULong(10, binaryString));
        setC2(getBitsAsULong(10, binaryString));
        setC3(getBitsAsULong(10, binaryString));
        setC4(getBitsAsULong(10, binaryString));
        setC5(getBitsAsULong(10, binaryString));
        setC6(getBitsAsULong(10, binaryString));
        setC7(calcMTM(getBitsAsULong(22, binaryString)));
        setC8(getBooleanBit(binaryString));
        setC9(getBooleanBit(binaryString));
        setC10(calcMTM(getBitsAsULong(22, binaryString)));
        setC11(getBooleanBit(binaryString));
        setC12(getBooleanBit(binaryString));
        setC13(calcMTM(getBitsAsULong(22, binaryString)));
        setC14(getBooleanBit(binaryString));
        setC15(getBooleanBit(binaryString));
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

    public boolean getC8() {
        return c8;
    }

    public void setC8(boolean c8) {
        this.c8 = c8;
    }

    public boolean getC9() {
        return c9;
    }

    public void setC9(boolean c9) {
        this.c9 = c9;
    }

    public double getC10() {
        return c10;
    }

    public void setC10(double c10) {
        this.c10 = c10;
    }

    public boolean getC11() {
        return c11;
    }

    public void setC11(boolean c11) {
        this.c11 = c11;
    }

    public boolean getC12() {
        return c12;
    }

    public void setC12(boolean c12) {
        this.c12 = c12;
    }

    public double getC13() {
        return c13;
    }

    public void setC13(double c13) {
        this.c13 = c13;
    }

    public boolean getC14() {
        return c14;
    }

    public void setC14(boolean c14) {
        this.c14 = c14;
    }

    public boolean getC15() {
        return c15;
    }

    public void setC15(boolean c15) {
        this.c15 = c15;
    }

    @Override
    public String toString() {
        return "HighResolutionEntity{" +
                "id=" + id +
                ", sequenceNumber=" + sequenceNumber +
                ", satelliteTime=" + satelliteTime +
                ", c1=" + c1 +
                ", c2=" + c2 +
                ", c3=" + c3 +
                ", c4=" + c4 +
                ", c5=" + c5 +
                ", c6=" + c6 +
                ", c7=" + c7 +
                ", c8=" + c8 +
                ", c9=" + c9 +
                ", c10=" + c10 +
                ", c11=" + c11 +
                ", c12=" + c12 +
                ", c13=" + c13 +
                ", c14=" + c14 +
                ", c15=" + c15 +
                '}';
    }

    @Transient
    private double calcMTM(long adc) {
        if ((adc & 0x00200000) == 0x00200000)
        {
            adc |= 0xFFC00000;
        }

        return adc * 4.0;
    }
}
