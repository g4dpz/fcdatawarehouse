package com.badgersoft.datawarehouse.eseo.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by davidjohnson on 17/10/2016.
 */
@Entity
@Table(name = "realtime", catalog = "eseo")
public class RealtimeEntity implements Serializable {

    private static Map<Long, Double> TRANSP_RSSI = new HashMap();
    private static Map<Long, Double> CMD_RSSI = new HashMap();
    private static Map<Long, Double> CMD_DOP = new HashMap();
    
    static {
        initTransponderRSSI();
        initCommandRSSI();
        initCommandDoppler();
    }

    private static void initTransponderRSSI() {
        double[][] refData = { { -120, Double.MIN_VALUE }, { -120, 108 }, { -118, 110 }, { -116, 112 }, { -115, 114 },
                { -114, 116 }, { -113, 117 }, { -112, 118 }, { -111, 120 }, { -110, 122 },
                { -109, 123 }, { -108, 124 }, { -107, 126 }, { -106, 128 }, { -105, 130 },
                { -104, 131 }, { -103, 133 }, { -102, 134 }, { -101, 136 }, { -100, 137 },
                { -99, 139 }, { -98, 140 }, { -96, 144 }, { -94, 147 }, { -92, 151 },
                { -90, 154 }, { -88, 157 }, { -86, 159 }, { -84, 161 }, { -82, 163 },
                { -80, 165 }, { -78, 168 }, { -76, 171 }, { -74, 174 }, { -72, 176 },
                { -70, 178 }, { -68, 179 }, { -66, 180 }, { -64, 181 }, {-64, Double.MAX_VALUE} };

        // calc values for all possible 8bit values
        for (int adc = 0; adc < 256; ++adc) {
            for (int j = 0; j < refData.length; j++) {
                if (adc != 0 && adc < refData[j][1]) {
                    double t1 = refData[j][0];
                    double a1 = refData[j][1];
                    double diffa = refData[j - 1][1] - a1;
                    double difft = refData[j - 1][0] - t1;
                    double value = ((adc - a1) * (difft / diffa)) + t1;
                    TRANSP_RSSI.put((long) adc, value);
                    break;
                }
            }
        }

    }

    private static void initCommandRSSI() {
        double[][] refData = { { -120, Double.MIN_VALUE }, { -120, 93 }, { -118, 95 }, { -117, 96 }, { -116, 98 },
                { -114, 100 }, { -113, 101 }, { -112, 103 }, { -111, 104 }, { -109, 106 }, { -108, 108 }, { -107, 109 },
                { -106, 110 }, { -105, 111 }, { -104, 113 }, { -103, 114 }, { -102, 116 }, { -101, 117 }, { -100, 118 },
                { -99, 119 }, { -98, 121 }, { -96, 124 }, { -94, 127 }, { -92, 130 }, { -90, 133 }, { -88, 135 },
                { -86, 136 }, { -84, 138 }, { -82, 140 }, { -80, 142 }, { -78, 145 }, { -76, 147 }, { -74, 150 },
                { -72, 152 }, { -70, 153 }, { -68, 155 }, { -64, 156 }, {-64, Double.MAX_VALUE} };

        // calc values for all possible 8bit values
        for (int adc = 0; adc < 256; ++adc) {
            for (int j = 0; j < refData.length; j++) {
                if (adc != 0 && adc < refData[j][1]) {
                    double t1 = refData[j][0];
                    double a1 = refData[j][1];
                    double diffa = refData[j - 1][1] - a1;
                    double difft = refData[j - 1][0] - t1;
                    double value = ((adc - a1) * (difft / diffa)) + t1;
                    CMD_RSSI.put((long) adc, value);
                    break;
                }
            }
        }

    }

    private static void initCommandDoppler() {
        // Command Doppler
        double[][] refData = { { -9, Double.MAX_VALUE }, { -9, 140 }, { -8, 139 }, { -7, 138 }, { -6, 136 }, { -5, 134 },
                { -4, 131 }, { -3, 128 }, { -2, 124 }, { -1, 120 }, { 0, 115 }, { +1, 110 }, { +2, 105 }, { +3, 100 },
                { +4, 95 }, { +5, 91 }, { +6, 87 }, { +7, 84 }, { +8, 82 }, { +9, 80 }, { +10, 78 }, { +11, 77 }, { +12, 76 },
                { +12, Double.MIN_VALUE }};

        // calc values for all possible 8bit values
        for (int adc = 255; adc >= 0; --adc) {
            for (int j = 0; j < refData.length; j++) {
                if (adc != 0 && adc > refData[j][1]) {
                    double t1 = refData[j][0];
                    double a1 = refData[j][1];
                    double diffa = refData[j - 1][1] - a1;
                    double difft = refData[j - 1][0] - t1;
                    double value = ((adc - a1) * (difft / diffa)) + t1;
                    CMD_DOP.put((long) adc, value);
                    break;
                }
            }
        }

    }

    public static void getRefData() {
        System.out.println(TRANSP_RSSI.toString());
        System.out.println(CMD_RSSI.toString());
        System.out.println(CMD_DOP.toString());
    }

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
    private double c9;
    private double c10;
    private double c11;
    private long c12;
    private long c13;
    private double c14;
    private double c15;
    private long c16;
    private long c17;
    private double c18;
    private double c19;
    private double c20;
    private double c21;
    private long c22;
    private long c23;
    private String c24;
    private String c25;
    private String c26;
    private String c27;
    private String c28;
    private String c29;
    private boolean c30;
    private String c31;

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

    public double getC9() {
        return c9;
    }

    public void setC9(double c9) {
        this.c9 = c9;
    }

    public double getC10() {
        return c10;
    }

    public void setC10(double c10) {
        this.c10 = c10;
    }

    public double getC11() {
        return c11;
    }

    public void setC11(double c11) {
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

    public double getC18() {
        return c18;
    }

    public void setC18(double c18) {
        this.c18 = c18;
    }

    public double getC19() {
        return c19;
    }

    public void setC19(double c19) {
        this.c19 = c19;
    }

    public double getC20() {
        return c20;
    }

    public void setC20(double c20) {
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

    public String getC26() {
        return c26;
    }

    public void setC26(String c26) {
        this.c26 = c26;
    }

    public String getC27() {
        return c27;
    }

    public void setC27(String c27) {
        this.c27 = c27;
    }

    public String getC28() {
        return c28;
    }

    public void setC28(String c28) {
        this.c28 = c28;
    }

    public String getC29() {
        return c29;
    }

    public void setC29(String c29) {
        this.c29 = c29;
    }

    public boolean getC30() {
        return c30;
    }

    public void setC30(boolean c30) {
        this.c30 = c30;
    }

    public String getC31() {
        return c31;
    }

    public void setC31(String c31) {
        this.c31 = c31;
    }

    public void readBinary(String binaryString) {

        // we skip the satelliteid/frame type preamble
        getBitsAsULong(16, binaryString);

        // AMSAT DATA
        // dcdcVoltage
        c1 = new VoltageMultiplierTelemetryValue(0.1, getBitsAsULong(8, binaryString)).calculate();

        // dcdcCurrent
        c2 = new MultiplierTelemetryValue(5.131579, getBitsAsULong(8, binaryString)).calculate();

        // dcdcTemp y = -0.7796212*x + 98.19402
        c3 = new MultiplierOffsetTelemetryValue(-0.7796212, 98.19402, getBitsAsULong(8, binaryString)).calculate();

        // enclosureTemp y = -0.7385868*x + 97.74249
        c4 = new MultiplierOffsetTelemetryValue(-0.7385868, 97.74249, getBitsAsULong(8, binaryString)).calculate();

        // processorTemp y = -0.7725984*x + 94.95152
        c5 = new MultiplierOffsetTelemetryValue(-0.7725984, 94.95152, getBitsAsULong(8, binaryString)).calculate();

        // 3v3Volts
        c6 = new VoltageMultiplierTelemetryValue(0.031141509, getBitsAsULong(8, binaryString)).calculate();

        // 3v3Current
        c7 = getBitsAsULong(8, binaryString);

        // transpSuppVolts
        c8 = new VoltageMultiplierTelemetryValue(0.0885753425, getBitsAsULong(8, binaryString)).calculate();

        // transpSuppCurr
        c9 = new MultiplierOffsetTelemetryValue(2.5941175, -5.188235, getBitsAsULong(8, binaryString)).calculate();

        // 9vVolts
        c10 = new VoltageMultiplierTelemetryValue(0.0881715, getBitsAsULong(8, binaryString)).calculate();

        // 9vCurr
        c11 = new MultiplierTelemetryValue(2.52778, getBitsAsULong(8, binaryString)).calculate();

        // revPwr
        c12 = calculateRfPower(getBitsAsULong(8, binaryString));

        // fwdPwr
        c13 = calculateRfPower(getBitsAsULong(8, binaryString));

        // fmAmpTemp y = y = -0.789929*x + 97.5934
        c14 = new MultiplierOffsetTelemetryValue(-0.789929, 97.5934, getBitsAsULong(8, binaryString)).calculate();

        // bpskAmpTemp y = -0.8104347*x + 91.93637
        c15 = new MultiplierOffsetTelemetryValue(-0.8104347, 91.93637, getBitsAsULong(8, binaryString)).calculate();

        // bpskAmpCurr
        c16 = (long) (2.18 * getBitsAsULong(8, binaryString));

        // bpsk3v3SuppCurr
        c17 = (long) (0.8 * getBitsAsULong(8, binaryString));

        // transpRSSI
        c18 = TRANSP_RSSI.get(getBitsAsULong(8, binaryString));

        // commandRSSI
        c19 = CMD_RSSI.get(getBitsAsULong(8, binaryString));

        // commandDopp
        c20 = CMD_DOP.get(getBitsAsULong(8, binaryString));

        // commandOscTemp y = -0.8592393*x + 94.30121
        c21 = new MultiplierOffsetTelemetryValue(-0.8592393 , 94.30121, getBitsAsULong(8, binaryString)).calculate();

        // seqNo
        c22 = getBitsAsULong(24, binaryString);

        // lastCommand
        c23 = getBitsAsULong(8, binaryString);

        // rfMode
        final long c24Value = getBitsAsULong(3, binaryString);
        c24 = new RFModeTelemetryValue(c24Value).getValueAsString();

        // dataMode
        c25 = new SatDataModeTelemetryValue(getBitsAsULong(2, binaryString)).getValueAsString();

        // payldTrfrStatus
        c26 = (getBooleanBit(binaryString)) ? "Downlink data to ground" : "Get data from payload";

        // inEclipseMode
        c27 = (getBooleanBit(binaryString) ? "Yes" : "No");

        // autoMode
        c28 = (getBooleanBit(binaryString) ? "B" : "A");

        // ctcssDetectState
        c29 = (getBooleanBit(binaryString) ? "On" : "Off");

        // safeModeState
        c30 = getBooleanBit(binaryString);

        // inSafeNode
        c31 = (getBooleanBit(binaryString) ? "Yes" : "No");
    }

    private long calculateRfPower(double c12raw) {
        return (long) ((Math.pow(c12raw, 2) * 0.0136) + (0.4995 * c12raw));
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
