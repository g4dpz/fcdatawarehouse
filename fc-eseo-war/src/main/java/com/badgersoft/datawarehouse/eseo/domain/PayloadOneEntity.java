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
public class PayloadOneEntity implements Serializable {

    @Transient
    private int stringPos = 211;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    235	Only 1	ESEO OBC	UNSIGNED16	PMM_VOLTAGE_SP1_STRING_1
    Solar panel voltage for eclipse detection
    251	Only 1	ESEO OBC	UNSIGNED16	PMM_VOLTAGE_SP2_STRING_1
    267	Only 1	ESEO OBC	UNSIGNED16	PMM_VOLTAGE_SP3_STRING_1
    283	Only 1	ESEO OBC	UNSIGNED8	ESEO OBD_MODE
    0x00: OBDH power up
    0x01: AOCS initialization
    0x02: AOCS damping
    0x04: AOCS normal SUN
    0x08: AOCS normal ECLIPSE
    0x10: Safe mode S1: minor main bus power down
    0x20: Safe mode S2: sever main bus power down
    0x40: Safe mode S3: major main bus power down
    291	Only 1	ESEO OBC	UNSIGNED20	ESEO OBD_EQUIPMENT_STATUS:
    0: TMTC main ON/OFF
    1: TMTC redundant ON/OFF
    2: Power Management Unit main ON/OFF
    3: Power Management Unit redundant ON/OFF
    4: Sun sensor main ON/OFF
    5: Sun sensor redundant ON/OFF
    6: Earth sensor ON/OFF
    7: Magnetometer main ON/OFF
    8: Magnetometer redundant ON/OFF
    9: Magnetic Torquer main ON/OFF
    10: Magnetic Torquer redundant ON/OFF
    11: Momentum Wheel main ON/OFF
    12: Momentum Wheel redundant ON/OFF
    13: TRITEL ON/OFF
    14: Langmuir Probe ON/OFF
    15: uCAM ON/OFF
    16: De-orbit mechanism ON/OFF
    17: AMSAT-UK ON/OFF (Always on)
    18: S-Band ON/OFF
    19: GPS receiver ON/OFF
    311	Only 1	ESEO OBC	UNSIGNED8	OBD_WD_RESET_COUNT
    319	Only 1	ESEO OBC	REAL32	ACS_OMEGA_P (Roll, deg/s)
    351	Only 1	ESEO OBC	REAL32	ACS_OMEGA_Q (Pitch, deg/s)
    383	Only 1	ESEO OBC	REAL32	ACS_OMEGA_R (Yaw, deg/s)
    415	Only 1	ESEO OBC	UNSIGNED8	STX_TEMP_4 (S-band Amplifier temperature)
    423	Only 1	ESEO OBC	UNSIGNED8	PMM_VOLTAGE_MB (16b) (Main Bus Voltage)

     */

    private long c1; // PMM_VOLTAGE_SP1_STRING_1
    private long c2; // PMM_VOLTAGE_SP1_STRING_2
    private long c3; // PMM_VOLTAGE_SP1_STRING_3
    private String c4; // ESEO OBD_MODE
    private Long c5; // ESEO OBD_EQUIPMENT_STATUS
    private long c6; // OBD_WD_RESET_COUNT
    private double c7; // Roll
    private double c8; // Pitch
    private double c9; // Yaw
    private long c10; // sband temp
    private long c11; // main bus volts

    @Column(name = "sequence_number")
    private Long sequenceNumber;

    @Column(name = "frame_type")
    private Long frameType;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "satellite_time")
    private Date satelliteTime;


    public PayloadOneEntity() {}

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

    public String getC4() {
        return c4;
    }

    public void setC4(String c4) {
        this.c4 = c4;
    }

    public Long getC5() {
        return c5;
    }

    public void setC5(Long c5) {
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
        c4 = getOBDMode(getBitsAsULong(8, binaryString));
        c5 = getBitsAsULong(20, binaryString);
        c6 = getBitsAsULong(8, binaryString);

        c7 = getBitsAsDouble(1, binaryString);
        c8 = getBitsAsDouble(1, binaryString);
        c9 = getBitsAsDouble(1, binaryString);
        c10 = getBitsAsULong(8, binaryString);
        c11 = getBitsAsULong(8, binaryString);
    }

    @Transient
    private String getOBDMode(long modeValue) {

        String[] modes = new String[] {
            "OBDH power up",
            "AOCS initialization",
            "AOCS damping",
            "AOCS normal SUN",
            "AOCS normal ECLIPSE",
            "Safe mode S1: minor main bus power down",
            "Safe mode S2: sever main bus power down",
            "Safe mode S3: major main bus power down",
                "Not defined (0x80)"
        };

        return modes[(int) (modeValue / 2)];
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
