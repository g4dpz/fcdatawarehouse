package com.badgersoft.datawarehouse.eseo.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by davidjohnson on 18/09/2016.
 */
@Entity
@Table(name = "payload_2", catalog = "eseo")
public class PayloadTwoEntity implements Serializable {

    @Transient
    private int stringPos = 211;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double c1; // ACS_ORBIT_x (Orbital position)
    private double c2; // ACS_ORBIT_y
    private double c3; // ACS_ORBIT_z
    private long c4; // PMM_AMSAT_CURRENT (16b) (AMSAT Switch current)
    private double c5; // MWM_VOLTAGE (Momentum wheel Measured DC-link voltage)
    private double c6; // MWM_CURRENT (Momentum wheel Measured current)
    private double c7; // MWM_OMEGAMESURED (Measured rotation speed)
    private long c8; // MPS_HPT01 (High Pressure Transducer measures tank pressure)
    private double c9; // PMM_TEMP_SP1_SENS_1 (Temp. of the solar panel 1)
    private double c10; // PMM_TEMP_BP1_SENS_1 (Temp. of battery pack 1)

    @Column(name = "sequence_number")
    private Long sequenceNumber;

    @Column(name = "frame_type")
    private Long frameType;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "satellite_time")
    private Date satelliteTime;

    @Transient
    public void readBinary(String binaryString) {

        // ESEO DATA
        c1 = getBitsAsDouble(1, binaryString);
        c2 = getBitsAsDouble(1, binaryString);
        c3 = getBitsAsDouble(1, binaryString);
        c4 = getBitsAsULong(16, binaryString);
        c5 = (double)getBitsAsULong(8, binaryString) * 0.01;
        c6 = (double)getBitsAsULong(16, binaryString) * 0.001;
        c7 = (double)getBitsAsULong(16, binaryString) * 0.0004875;
        c8 = getBitsAsSLong(16, binaryString);
        c9 = (double)getBitsAsSLong(12, binaryString) * 0.1;
        c10 = (double)getBitsAsSLong(12, binaryString) * 0.1;
    }

    public Long getId() {
        return id;
    }

    public double getC1() {
        return c1;
    }

    public double getC2() {
        return c2;
    }

    public double getC3() {
        return c3;
    }

    public long getC4() {
        return c4;
    }

    public double getC5() {
        return c5;
    }

    public double getC6() {
        return c6;
    }

    public double getC7() {
        return c7;
    }

    public long getC8() {
        return c8;
    }

    public double getC9() {
        return c9;
    }

    public double getC10() {
        return c10;
    }

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public Long getFrameType() {
        return frameType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getSatelliteTime() {
        return satelliteTime;
    }

    @Transient
    private long getBitsAsULong(int length, String binaryString) {

        final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + length), 2);
        stringPos += length;
        return value;
    }

    @Transient
    private boolean getBooleanBit(String binaryString) {
        final boolean value = (binaryString.substring(stringPos, stringPos + 1).equals("1"));
        stringPos += 1;
        return value;
    }

    @Transient
    private float getBitsAsFloat(int length, String binaryString) {
        int intBits = new BigInteger(binaryString.substring(stringPos, stringPos + length), 2).intValue();
        stringPos += length;
        return Float.intBitsToFloat(intBits);
    }

    @Transient
    private double getBitsAsDouble(int length, String binaryString) {
        long bits = new BigInteger(binaryString.substring(stringPos, stringPos + length), 2).intValue();
        stringPos += length;
        return Double.longBitsToDouble(bits);
    }

    @Transient
    private long getBitsAsSLong(int i, String binaryString) {
        long multiplier = (binaryString.charAt(0) == '0') ? 1L : -1L;
        stringPos += 1;
        return (getBitsAsULong(15, binaryString) * multiplier);
    }


    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public void setFrameType(Long frameType) {
        this.frameType = frameType;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setSatelliteTime(Date satelliteTime) {
        this.satelliteTime = satelliteTime;
    }
}
