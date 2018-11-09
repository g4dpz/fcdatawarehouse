package com.badgersoft.datawarehouse.eseo.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by davidjohnson on 17/10/2016.
 */
@MappedSuperclass
abstract class BaseRealtimeEntity implements Serializable {

    @Transient
    protected int stringPos = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sequence_number")
    private Long sequenceNumber;

    @Column(name = "frame_type")
    private Long frameType;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "satellite_time")
    private Date satelliteTime;

    private String latitude;
    private String longitude;

    private double c1;
    private double c2;
    private double c3;
    private double c4;
    private double c5;
    private double c6;
    private long c7;
    private double c8;
    private long c9;
    private double c10;
    private long c11;
    private long c12;
    private long c13;
    private double c14;
    private double c15;
    private long c16;
    private long c17;
    private long c18;
    private long c19;
    private long c20;
    private double c21;
    private long c22;
    private long c23;
    private String c24;
    private String c25;
    private boolean c26;
    private boolean c27;
    private boolean c28;
    private boolean c29;
    private boolean c30;
    private boolean c31;

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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public long getC7() {
        return c7;
    }

    public void setC7(long c7) {
        this.c7 = c7;
    }

    public double getC8() {
        return c8;
    }

    public void setC8(double c8) {
        this.c8 = c8;
    }

    public long getC9() {
        return c9;
    }

    public void setC9(long c9) {
        this.c9 = c9;
    }

    public double getC10() {
        return c10;
    }

    public void setC10(double c10) {
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

    public double getC14() {
        return c14;
    }

    public void setC14(double c14) {
        this.c14 = c14;
    }

    public double getC15() {
        return c15;
    }

    public void setC15(double c15) {
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

    public long getC20() {
        return c20;
    }

    public void setC20(long c20) {
        this.c20 = c20;
    }

    public double getC21() {
        return c21;
    }

    public void setC21(double c21) {
        this.c21 = c21;
    }

    public long getC22() {
        return c22;
    }

    public void setC22(long c22) {
        this.c22 = c22;
    }

    public long getC23() {
        return c23;
    }

    public void setC23(long c23) {
        this.c23 = c23;
    }

    public String getC24() {
        return c24;
    }

    public void setC24(String c24) {
        this.c24 = c24;
    }

    public String getC25() {
        return c25;
    }

    public void setC25(String c25) {
        this.c25 = c25;
    }

    public boolean isC26() {
        return c26;
    }

    public void setC26(boolean c26) {
        this.c26 = c26;
    }

    public boolean isC27() {
        return c27;
    }

    public void setC27(boolean c27) {
        this.c27 = c27;
    }

    public boolean isC28() {
        return c28;
    }

    public void setC28(boolean c28) {
        this.c28 = c28;
    }

    public boolean isC29() {
        return c29;
    }

    public void setC29(boolean c29) {
        this.c29 = c29;
    }

    public boolean isC30() {
        return c30;
    }

    public void setC30(boolean c30) {
        this.c30 = c30;
    }

    public boolean isC31() {
        return c31;
    }

    public void setC31(boolean c31) {
        this.c31 = c31;
    }

    protected void readBinary(String binaryString) {

        // AMSAT DATA
        c1 = new VoltageMultiplierTelemetryValue(0.07242, getBitsAsULong(8, binaryString)).calculate();
        c2 = new MultiplierTelemetryValue(2.019, getBitsAsULong(8, binaryString)).calculate();
        c3 = new EseoTempTelemetryValue(getBitsAsULong(8, binaryString)).calculate();
        c4 = new EseoTempTelemetryValue(getBitsAsULong(8, binaryString)).calculate();
        c5 = new EseoTempTelemetryValue(getBitsAsULong(8, binaryString)).calculate();
        c6 = new VoltageMultiplierTelemetryValue(0.0129412, getBitsAsULong(8, binaryString)).calculate();
        c7 = getBitsAsULong(8, binaryString);
        c8 = new VoltageMultiplierTelemetryValue(0.04852, getBitsAsULong(8, binaryString)).calculate();
        c9 = getBitsAsULong(8, binaryString);
        c10 = new VoltageMultiplierTelemetryValue(0.049185, getBitsAsULong(8, binaryString)).calculate();
        c11 = getBitsAsULong(8, binaryString);
        c12 = getBitsAsULong(8, binaryString);
        c13 = getBitsAsULong(8, binaryString);

        c14 = new EseoFmAmpTempTelemetryValue(getBitsAsULong(8, binaryString)).calculate();
        c15 = new EseoBpskAmpTempTelemetryValue(getBitsAsULong(8, binaryString)).calculate();
        c16 = getBitsAsULong(8, binaryString);
        c17 = getBitsAsULong(8, binaryString);

        c18 = getBitsAsULong(8, binaryString);
        c19 = getBitsAsULong(8, binaryString);
        c20 = getBitsAsULong(8, binaryString);
        c21 = new EseoCmdOscTempTelemetryValue(getBitsAsULong(8, binaryString)).calculate();

        c22 = getBitsAsULong(24, binaryString);
        c23 = getBitsAsULong(8, binaryString);

        final long c24Value = getBitsAsULong(3, binaryString);
        c24 = new RFModeTelemetryValue(c24Value).getValueAsString();
        c25 = new SatDataModeTelemetryValue(getBitsAsULong(2, binaryString)).getValueAsString();

        c26 = getBooleanBit(binaryString);
        c27 = getBooleanBit(binaryString);
        c28 = getBooleanBit(binaryString);
        c29 = getBooleanBit(binaryString);
        c30 = getBooleanBit(binaryString);
        c31 = getBooleanBit(binaryString);
    }


    @Transient
    protected long getBitsAsULong(int length, String binaryString) {

        final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + length), 2);
        stringPos += length;
        return value;
    }

    @Transient
    protected boolean getBooleanBit(String binaryString) {
        final boolean value = (binaryString.substring(stringPos, stringPos + 1).equals("1"));
        stringPos += 1;
        return value;
    }

    @Transient
    protected float getBitsAsFloat(int length, String binaryString) {
        int intBits = new BigInteger(binaryString.substring(stringPos, stringPos + length), 2).intValue();
        stringPos += length;
        return Float.intBitsToFloat(intBits);
    }

    @Transient
    protected double getBitsAsDouble(int length, String binaryString) {
        long bits = new BigInteger(binaryString.substring(stringPos, stringPos + length), 2).intValue();
        stringPos += length;
        return Double.longBitsToDouble(bits);
    }

    @Transient
    protected long getBitsAsSLong(int i, String binaryString) {
        long multiplier = (binaryString.charAt(0) == '0') ? 1L : -1L;
        stringPos += 1;
        return (getBitsAsULong(15, binaryString) * multiplier);
    }

}
