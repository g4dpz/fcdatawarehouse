package com.badgersoft.datawarehouse.funcube.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by davidjohnson on 18/09/2016.
 */
@Entity
@Table(name = "realtime")
public class RealtimeEntity extends TelemetryEntity {

    private static final double[] ANTS_TEMPS = new double[256];

    static {
        PA_TEMPS_256 = buildLookupTable(256);
        PA_TEMPS_1024 = buildLookupTable(1024);
        setupAntsTemps();
        setupSunSensors();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sequence_number")
    Long sequenceNumber;

    @Column(name = "frame_type")
    Long frameType;

    @Column(name = "created_date")
    Date createdDate;

    @Column(name = "satellite_time")
    Date satelliteTime;

    String latitude;
    String longitude;

    // EPS
    private long c1;
    private long c2;
    private long c3;
    private long c4;
    private long c5;
    private long c6;
    private long c7;
    private long c8;
    private long c9;
    private long c10;
    private long c11;
    private long c12;
    private long c13;
    private long c14;
    private long c15;
    private long c16;

    // ASIB
    private double c17;
    private double c18;
    private double c19;
    private double c20;
    private double c21;
    private double c22;
    private double c23;
    private long c24;
    private long c25;
    private long c26;

    // RF
    private long c27;
    private long c28;
    private double c29;
    private long c30;
    private long c31;
    private long c32;

    // PA
    private double c33;
    private double c34;
    private double c35;
    private double c36;

    // ANTS
    private double c37;
    private double c38;
    private double c39;
    private boolean c40;
    private boolean c41;
    private boolean c42;
    private boolean c43;

    // SW
    private long c44;
    private long c45;
    private boolean c46;
    private boolean c47;
    private boolean c48;
    private boolean c49;
    private boolean c50;
    private boolean c51;
    private boolean c52;
    private boolean c53;
    private boolean c54;
    private boolean c55;
    private boolean c56;
    private boolean c57;
    private boolean c58;


    public RealtimeEntity() {}

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

    public long getC7() {
        return c7;
    }

    public void setC7(long c7) {
        this.c7 = c7;
    }

    public long getC8() {
        return c8;
    }

    public void setC8(long c8) {
        this.c8 = c8;
    }

    public long getC9() {
        return c9;
    }

    public void setC9(long c9) {
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

    public double getC17() {
        return c17;
    }

    public void setC17(double c17) {
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

    public double getC22() {
        return c22;
    }

    public void setC22(double c22) {
        this.c22 = c22;
    }

    public double getC23() {
        return c23;
    }

    public void setC23(double c23) {
        this.c23 = c23;
    }

    public long getC24() {
        return c24;
    }

    public void setC24(long c24) {
        this.c24 = c24;
    }

    public long getC25() {
        return c25;
    }

    public void setC25(long c25) {
        this.c25 = c25;
    }

    public long getC26() {
        return c26;
    }

    public void setC26(long c26) {
        this.c26 = c26;
    }

    public long getC27() {
        return c27;
    }

    public void setC27(long c27) {
        this.c27 = c27;
    }

    public long getC28() {
        return c28;
    }

    public void setC28(long c28) {
        this.c28 = c28;
    }

    public double getC29() {
        return c29;
    }

    public void setC29(double c29) {
        this.c29 = c29;
    }

    public long getC30() {
        return c30;
    }

    public void setC30(long c30) {
        this.c30 = c30;
    }

    public long getC31() {
        return c31;
    }

    public void setC31(long c31) {
        this.c31 = c31;
    }

    public long getC32() {
        return c32;
    }

    public void setC32(long c32) {
        this.c32 = c32;
    }

    public double getC33() {
        return c33;
    }

    public void setC33(double c33) {
        this.c33 = c33;
    }

    public double getC34() {
        return c34;
    }

    public void setC34(double c34) {
        this.c34 = c34;
    }

    public double getC35() {
        return c35;
    }

    public void setC35(double c35) {
        this.c35 = c35;
    }

    public double getC36() {
        return c36;
    }

    public void setC36(double c36) {
        this.c36 = c36;
    }

    public double getC37() {
        return c37;
    }

    public void setC37(double c37) {
        this.c37 = c37;
    }

    public double getC38() {
        return c38;
    }

    public void setC38(double c38) {
        this.c38 = c38;
    }

    public double getC39() {
        return c39;
    }

    public void setC39(double c39) {
        this.c39 = c39;
    }

    public boolean getC40() {
        return c40;
    }

    public void setC40(boolean c40) {
        this.c40 = c40;
    }

    public boolean getC41() {
        return c41;
    }

    public void setC41(boolean c41) {
        this.c41 = c41;
    }

    public boolean getC42() {
        return c42;
    }

    public void setC42(boolean c42) {
        this.c42 = c42;
    }

    public boolean getC43() {
        return c43;
    }

    public void setC43(boolean c43) {
        this.c43 = c43;
    }

    public long getC44() {
        return c44;
    }

    public void setC44(long c44) {
        this.c44 = c44;
    }

    public long getC45() {
        return c45;
    }

    public void setC45(long c45) {
        this.c45 = c45;
    }

    public boolean getC46() {
        return c46;
    }

    public void setC46(boolean c46) {
        this.c46 = c46;
    }

    public boolean getC47() {
        return c47;
    }

    public void setC47(boolean c47) {
        this.c47 = c47;
    }

    public boolean getC48() {
        return c48;
    }

    public void setC48(boolean c48) {
        this.c48 = c48;
    }

    public boolean getC49() {
        return c49;
    }

    public void setC49(boolean c49) {
        this.c49 = c49;
    }

    public boolean getC50() {
        return c50;
    }

    public void setC50(boolean c50) {
        this.c50 = c50;
    }

    public boolean getC51() {
        return c51;
    }

    public void setC51(boolean c51) {
        this.c51 = c51;
    }

    public boolean getC52() {
        return c52;
    }

    public void setC52(boolean c52) {
        this.c52 = c52;
    }

    public boolean getC53() {
        return c53;
    }

    public void setC53(boolean c53) {
        this.c53 = c53;
    }

    public boolean getC54() {
        return c54;
    }

    public void setC54(boolean c54) {
        this.c54 = c54;
    }

    public boolean getC55() {
        return c55;
    }

    public void setC55(boolean c55) {
        this.c55 = c55;
    }

    public boolean getC56() {
        return c56;
    }

    public void setC56(boolean c56) {
        this.c56 = c56;
    }

    public boolean getC57() {
        return c57;
    }

    public void setC57(boolean c57) {
        this.c57 = c57;
    }

    public boolean getC58() {
        return c58;
    }

    public void setC58(boolean c58) {
        this.c58 = c58;
    }

    @Transient
    private int stringPos = 0;

    @Transient
    public void readBinary(String binaryString) {
        // Solar Panel Voltage X
        c1 = getBitsAsULong(16, binaryString);
        // Solar Panel Voltage Y
        c2 = getBitsAsULong(16, binaryString);
        // Solar Panel Voltage Z
        c3 = getBitsAsULong(16, binaryString);
        // Total Photo Current
        c4 = getBitsAsULong(16, binaryString);
        // Battery Voltage
        c5 = getBitsAsULong(16, binaryString);
        // Total System Current
        c6 = getBitsAsULong(16, binaryString);
        // Reboot Count
        c7 = getBitsAsULong(16, binaryString);
        // EPS Software Errors
        c8 = getBitsAsULong(16, binaryString);
        // Boost Converter Temp 1 C
        c9 = twosComplement(getBitsAsULong(8, binaryString));
        // Boost Converter Temp 2 C
        c10 = twosComplement(getBitsAsULong(8, binaryString));
        // Boost Converter Temp 3 C
        c11 = twosComplement(getBitsAsULong(8, binaryString));
        // Battery Temp C
        c12 = twosComplement(getBitsAsULong(8, binaryString));
        // Latch up count 5v SW1
        c13 = getBitsAsULong(8, binaryString);
        // Channel current on 5v SW1
        c14 = getBitsAsULong(8, binaryString);
        // Reset cause
        c15 = getBitsAsULong(8, binaryString);
        // Power point tracking mode
        c16 = getBitsAsULong(8, binaryString);

        // Sun Sensor X+
        c17 = SOL_ILLUMINATION[(int)getBitsAsULong(10, binaryString)];
        // Sun Sensor X-
        c18 = SOL_ILLUMINATION[(int)getBitsAsULong(10, binaryString)];
        // Sun Sensor Y+
        c19 = SOL_ILLUMINATION[(int)getBitsAsULong(10, binaryString)];

        // Solar Panel Temp X+
        c20 = scale(getBitsAsULong(10, binaryString), -0.2073, 158.239);
        // Solar Panel Temp X-
        c21 = scale(getBitsAsULong(10, binaryString), -0.2083, 159.227);
        // Solar Panel Temp Y+
        c22 = scale(getBitsAsULong(10, binaryString), -0.2076, 158.656);
        // Solar Panel Temp Y+
        c23 = scale(getBitsAsULong(10, binaryString), -0.2087, 159.045);

        // 3v3 Bus Volts
        c24 = getBitsAsULong(10, binaryString) * 4;
        // 3v3 Bus Curr
        c25 = getBitsAsULong(10, binaryString);
        // 5v0 Bus Volts
        c26 = getBitsAsULong(10, binaryString) * 6;

        // --- RF Section ---
        // Receiver Doppler
        c27 = getBitsAsULong(8, binaryString);
        // Receiver RSSI
        c28 = getBitsAsULong(8, binaryString);

        // Receiver Temperature
        c29 = scale(getBitsAsULong(8, binaryString), -0.857, 193.672);
        // Receive current
        c31 = getBitsAsULong(8, binaryString);
        // Transmit current 3.3V bus
        c32 = getBitsAsULong(8, binaryString);
        // Transmit current 5.0V bus
        c33 = getBitsAsULong(8, binaryString);

        // --- PA Section
        // Reverse power
        c34 = (0.005 * Math.pow(getBitsAsULong(8, binaryString), 2.0629));
        // Forward power
        c35 = (0.005 * Math.pow(getBitsAsULong(8, binaryString), 2.0629));
        // Transmitter Board temperature
        c36 = PA_TEMPS_256[(int) getBitsAsULong(8, binaryString)];
        // Transmitter Board current
        c37 = scale(getBitsAsULong(8, binaryString), 0.5496, 2.5425);


        // Antenna temp 0
        c38 = ANTS_TEMPS[(int) getBitsAsULong(8, binaryString)];
        // Antenna temp 1
        c39 = ANTS_TEMPS[(int) getBitsAsULong(8, binaryString)];

        // Antenna deployment 0
        c40 = getBooleanBit(binaryString);
        // Antenna deployment 1
        c41 = getBooleanBit(binaryString);
        // Antenna deployment 2
        c42 = getBooleanBit(binaryString);
        // Antenna deployment 3
        c43 = getBooleanBit(binaryString);
        // sequence number (we already know)
        getBitsAsULong(24, binaryString);

        // DTMF command count
        c44 = getBitsAsULong(6, binaryString);
        // DTMF last command
        c45 = getBitsAsULong(5, binaryString);

        // DTMF command success
        c46 = getBooleanBit(binaryString);
        // Data valid ASIB
        c47 = getBooleanBit(binaryString);
        // Data valid EPS
        c48 = getBooleanBit(binaryString);
        // Data valid PA
        c49 = getBooleanBit(binaryString);
        // Data valid RF
        c50 = getBooleanBit(binaryString);
        // Data valid iMTQ
        c51 = getBooleanBit(binaryString);
        // Data valid ANTS bus-B
        c52 = getBooleanBit(binaryString);
        // Data valid ANTS bus-A
        c53 = getBooleanBit(binaryString);
        // In eclipse mode
        c54 = getBooleanBit(binaryString);
        // In safe mode
        c55 = getBooleanBit(binaryString);
        // Hardware ABF On/Off
        c56 = getBooleanBit(binaryString);
        // Software ABF On/Off
        c57 = getBooleanBit(binaryString);
        // Deployment wait at next boot
        c58 = getBooleanBit(binaryString);
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

    private static void setupAntsTemps() {
        // data from ANTS manual, start and end values added
        // to ensure out of range is obvious (-255 or +255)
        int[][] tempToAdc = new int[][] { { -255, Integer.MIN_VALUE }, { -255, 2616 }, { -50, 2616 }, { -49, 2607 }, { -48, 2598 },
                { -47, 2589 }, { -46, 2580 }, { -45, 2571 }, { -44, 2562 }, { -43, 2553 }, { -42, 2543 }, { -41, 2533 }, { -40, 2522 },
                { -39, 2512 }, { -38, 2501 }, { -37, 2491 }, { -36, 2481 }, { -35, 2470 }, { -34, 2460 }, { -33, 2449 }, { -32, 2439 },
                { -31, 2429 }, { -30, 2418 }, { -29, 2408 }, { -28, 2397 }, { -27, 2387 }, { -26, 2376 }, { -25, 2366 }, { -24, 2355 },
                { -23, 2345 }, { -22, 2334 }, { -21, 2324 }, { -20, 2313 }, { -19, 2302 }, { -18, 2292 }, { -17, 2281 }, { -16, 2271 },
                { -15, 2260 }, { -14, 2250 }, { -13, 2239 }, { -12, 2228 }, { -11, 2218 }, { -10, 2207 }, { -9, 2197 }, { -8, 2186 },
                { -7, 2175 }, { -6, 2164 }, { -5, 2154 }, { -4, 2143 }, { -3, 2132 }, { -2, 2122 }, { -1, 2111 }, { 0, 2100 }, { 1, 2089 },
                { 2, 2079 }, { 3, 2068 }, { 4, 2057 }, { 5, 2047 }, { 6, 2036 }, { 7, 2025 }, { 8, 2014 }, { 9, 2004 }, { 10, 1993 },
                { 11, 1982 }, { 12, 1971 }, { 13, 1961 }, { 14, 1950 }, { 15, 1939 }, { 16, 1928 }, { 17, 1918 }, { 18, 1907 },
                { 19, 1896 }, { 20, 1885 }, { 21, 1874 }, { 22, 1864 }, { 23, 1853 }, { 24, 1842 }, { 25, 1831 }, { 26, 1820 },
                { 27, 1810 }, { 28, 1799 }, { 29, 1788 }, { 30, 1777 }, { 31, 1766 }, { 32, 1756 }, { 33, 1745 }, { 34, 1734 },
                { 35, 1723 }, { 36, 1712 }, { 37, 1701 }, { 38, 1690 }, { 39, 1679 }, { 40, 1668 }, { 41, 1657 }, { 42, 1646 },
                { 43, 1635 }, { 44, 1624 }, { 45, 1613 }, { 46, 1602 }, { 47, 1591 }, { 48, 1580 }, { 49, 1569 }, { 50, 1558 },
                { 51, 1547 }, { 52, 1536 }, { 53, 1525 }, { 54, 1514 }, { 55, 1503 }, { 56, 1492 }, { 57, 1481 }, { 58, 1470 },
                { 59, 1459 }, { 60, 1448 }, { 61, 1436 }, { 62, 1425 }, { 63, 1414 }, { 64, 1403 }, { 65, 1391 }, { 66, 1380 },
                { 67, 1369 }, { 68, 1358 }, { 69, 1346 }, { 70, 1335 }, { 71, 1324 }, { 72, 1313 }, { 73, 1301 }, { 74, 1290 },
                { 75, 1279 }, { 76, 1268 }, { 77, 1257 }, { 78, 1245 }, { 79, 1234 }, { 80, 1223 }, { 81, 1212 }, { 82, 1201 },
                { 83, 1189 }, { 84, 1178 }, { 85, 1167 }, { 86, 1155 }, { 87, 1144 }, { 88, 1133 }, { 89, 1122 }, { 90, 1110 },
                { 91, 1099 }, { 92, 1088 }, { 93, 1076 }, { 94, 1065 }, { 95, 1054 }, { 96, 1042 }, { 97, 1031 }, { 98, 1020 },
                { 99, 1008 }, { 100, 997 }, { 101, 986 }, { 102, 974 }, { 103, 963 }, { 104, 951 }, { 105, 940 }, { 106, 929 },
                { 107, 917 }, { 108, 906 }, { 109, 895 }, { 110, 883 }, { 111, 872 }, { 112, 860 }, { 113, 849 }, { 114, 837 },
                { 115, 826 }, { 116, 814 }, { 117, 803 }, { 118, 791 }, { 119, 780 }, { 120, 769 }, { 121, 757 }, { 122, 745 },
                { 123, 734 }, { 124, 722 }, { 125, 711 }, { 126, 699 }, { 127, 688 }, { 128, 676 }, { 129, 665 }, { 130, 653 },
                { 131, 642 }, { 132, 630 }, { 133, 618 }, { 134, 607 }, { 135, 595 }, { 136, 584 }, { 137, 572 }, { 138, 560 },
                { 139, 549 }, { 140, 537 }, { 141, 525 }, { 142, 514 }, { 143, 502 }, { 144, 490 }, { 145, 479 }, { 146, 467 },
                { 147, 455 }, { 148, 443 }, { 149, 432 }, { 150, 420 }, { 255, 420 }, { 255, Integer.MIN_VALUE } };
        for (int i = 0; i < 256; ++i) {
            // calc values for all possible 8bit values
            double adc = (i * 3300.0) / 255.75;
            for (int j = 0; j < tempToAdc.length; j++) {
                if (j != 0 && adc > tempToAdc[j][1]) {
                    double t1 = tempToAdc[j][0];
                    double a1 = tempToAdc[j][1];
                    double diffa = tempToAdc[j - 1][1] - a1;
                    double difft = tempToAdc[j - 1][0] - t1;
                    ANTS_TEMPS[i] = ((adc - a1) * (difft / diffa)) + t1;
                    break;
                }
            }
        }
    }
}
