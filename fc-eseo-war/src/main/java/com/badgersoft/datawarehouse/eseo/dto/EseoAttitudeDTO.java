package com.badgersoft.datawarehouse.eseo.dto;

import com.badgersoft.datawarehouse.eseo.domain.PayloadOneEntity;
import com.badgersoft.datawarehouse.eseo.domain.PayloadTwoEntity;

import java.io.Serializable;

public class EseoAttitudeDTO implements Serializable {

    private String roll;
    private String pitch;
    private String yaw;
    private String xPosition;
    private String yPosition;
    private String zPosition;


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
