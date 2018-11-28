package com.badgersoft.datawarehouse.eseo.dto;

import com.badgersoft.datawarehouse.eseo.domain.PayloadOneEntity;
import com.badgersoft.datawarehouse.eseo.domain.PayloadTwoEntity;

import java.io.Serializable;

public class EseoAttitudeDTO extends BaseDTO implements Serializable {

    private String roll;
    private String pitch;
    private String yaw;
    private String xPosition;
    private String yPosition;
    private String zPosition;

    /* payload_1
    private Long c1; // PMM_VOLTAGE_SP1_STRING_1
    private Long c2; // PMM_VOLTAGE_SP1_STRING_2
    private Long c3; // PMM_VOLTAGE_SP1_STRING_3
    private String c4; // ESEO OBD_MODE
    private String c5; // ESEO OBD_EQUIPMENT_STATUS
    private Long c6; // OBD_WD_RESET_COUNT
    private double c7; // Roll
    private double c8; // Pitch
    private double c9; // Yaw
    private Long c10; // sband temp
    private Long c11; // main bus volts
     */


    /*
    payloadTwoEntity

    private double c1; // ACS_ORBIT_x (Orbital position)
    private double c2; // ACS_ORBIT_y
    private double c3; // ACS_ORBIT_z
    private long c4; // PMM_AMSAT_CURRENT (16b) (AMSAT Switch current)
    private long c5; // MWM_VOLTAGE (Momentum wheel Measured DC-link voltage)
    private long c6; // MWM_CURRENT (Momentum wheel Measured current)
    private long c7; // MWM_OMEGAMESURED (Measured rotation speed)
    private long c8; // MPS_HPT01 (High Pressure Transducer measures tank pressure)
    private long c9; // PMM_TEMP_SP1_SENS_1 (Temp. of the solar panel 1)
    private long c10; // PMM_TEMP_BP1_SENS_1 (Temp. of battery pack 1)
     */


    public EseoAttitudeDTO(PayloadOneEntity payloadOneEntity, PayloadTwoEntity payloadTwoEntity) {
        roll = formatOneDP(payloadOneEntity.getC7()) + " deg./s";
        pitch = formatOneDP(payloadOneEntity.getC8()) + " deg./s";
        yaw = formatOneDP(payloadOneEntity.getC9()) + " deg./s";
        xPosition = formatOneDP(payloadTwoEntity.getC1()) + " km";
        yPosition = formatOneDP(payloadTwoEntity.getC2()) + " km";
        zPosition = formatOneDP(payloadOneEntity.getC3()) + " km";
    }

    public String getRoll() {
        return roll;
    }

    public String getPitch() {
        return pitch;
    }

    public String getYaw() {
        return yaw;
    }

    public String getxPosition() {
        return xPosition;
    }

    public String getyPosition() {
        return yPosition;
    }

    public String getzPosition() {
        return zPosition;
    }
}
