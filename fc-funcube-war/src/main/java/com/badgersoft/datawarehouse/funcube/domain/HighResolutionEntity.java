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

    static {
        setupSunSensors();
    }

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

    public HighResolutionEntity() {
    }

    public HighResolutionEntity(long sequenceNumber, Date satelliteTime, String binaryString) {
        setSequenceNumber(sequenceNumber);
        setSatelliteTime(satelliteTime);
        setC1(SOL_ILLUMINATION[(int) getBitsAsULong(10, binaryString)]);
        setC2(SOL_ILLUMINATION[(int) getBitsAsULong(10, binaryString)]);
        setC3(SOL_ILLUMINATION[(int) getBitsAsULong(10, binaryString)]);
        setC4(SOL_ILLUMINATION[(int) getBitsAsULong(10, binaryString)]);
        setC5(SOL_ILLUMINATION[(int) getBitsAsULong(10, binaryString)]);
        setC6(getBitsAsULong(15, binaryString) * 2.0);
        setC7(getBitsAsULong(15, binaryString) * 2.0);
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
}
