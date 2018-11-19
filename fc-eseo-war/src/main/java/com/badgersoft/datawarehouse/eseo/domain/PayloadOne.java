package com.badgersoft.datawarehouse.eseo.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by davidjohnson on 18/09/2016.
 */
@Entity
@Table(name = "payload_1", catalog = "eseo")
public class PayloadOne implements Serializable {

    @Transient
    private int stringPos = 211;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long c1;
    private long c2;
    private long c3;
    private long c4;
    private long c5;
    private long c6;
    private double c7;
    private double c8;
    private double c9;
    private long c10;
    private long c11;

    @Column(name = "sequence_number")
    private Long sequenceNumber;

    @Column(name = "frame_type")
    private Long frameType;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "satellite_time")
    private Date satelliteTime;


    public PayloadOne() {}

    public long getC1() {
        return c1;
    }

    public void setC1(long c1) {
        this.c1 = c1;
    }

    public long getC2() {
        return c2;
    }

    public void setC2(long c2) {
        this.c2 = c2;
    }

    public long getC3() {
        return c3;
    }

    public void setC3(long c3) {
        this.c3 = c3;
    }

    public long getC4() {
        return c4;
    }

    public void setC4(long c4) {
        this.c4 = c4;
    }

    public long getC5() {
        return c5;
    }

    public void setC5(long c5) {
        this.c5 = c5;
    }

    public long getC6() {
        return c6;
    }

    public void setC6(long c6) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Long getFrameType() {
        return frameType;
    }

    public void setFrameType(Long frameType) {
        this.frameType = frameType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getSatelliteTime() {
        return satelliteTime;
    }

    public void setSatelliteTime(Date satelliteTime) {
        this.satelliteTime = satelliteTime;
    }

    @Transient
    public void readBinary(String binaryString) {

        // AMSAT DATA
        c1 = getBitsAsULong(16, binaryString);
        c2 = getBitsAsULong(16, binaryString);
        c3 = getBitsAsULong(16, binaryString);
        c4 = getBitsAsULong(8, binaryString);
        c5 = getBitsAsULong(20, binaryString);
        c6 = getBitsAsULong(8, binaryString);

        c7 = getBitsAsDouble(1, binaryString);
        c8 = getBitsAsDouble(1, binaryString);
        c9 = getBitsAsDouble(1, binaryString);
        c10 = getBitsAsULong(8, binaryString);
        c11 = getBitsAsULong(8, binaryString);
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


}
