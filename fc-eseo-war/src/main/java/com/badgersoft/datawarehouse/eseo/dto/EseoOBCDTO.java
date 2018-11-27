package com.badgersoft.datawarehouse.eseo.dto;

import com.badgersoft.datawarehouse.eseo.domain.PayloadOneEntity;
import com.badgersoft.datawarehouse.eseo.domain.PayloadTwoEntity;

import java.io.Serializable;

public class EseoOBCDTO implements Serializable {

    private Long voltageSolarPanel1;
    private Long voltageSolarPanel2;
    private Long voltageSolarPanel3;
    private String mode;
    private Long wdResetCount;
    private Long sBandAmpTemp;
    private Long mainBusVoltage;
    private Long amsatSwitchCurrent;
    private Long momentumWheelVoltage;
    private Long momentumWheelCurrent;
    private Long momentumWheelSpeed;
    private Long tankPressure;
    private Long temperatureSolarPanel1;
    private Long temperatureBattery1;

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

    /* payload_2
    private double c1; // ACS_ORBIT_x (Orbital position)
    private double c2; // ACS_ORBIT_y
    private double c3; // ACS_ORBIT_z
    private Long c4; // PMM_AMSAT_CURRENT (16b) (AMSAT Switch current)
    private Long c5; // MWM_VOLTAGE (Momentum wheel Measured DC-link voltage)
    private Long c6; // MWM_CURRENT (Momentum wheel Measured current)
    private Long c7; // MWM_OMEGAMESURED (Measured rotation speed)
    private Long c8; // MPS_HPT01 (High Pressure Transducer measures tank pressure)
    private Long c9; // PMM_TEMP_SP1_SENS_1 (Temp. of the solar panel 1)
    private Long c10; // PMM_TEMP_BP1_SENS_1 (Temp. of battery pack 1)
     */
    
    public EseoOBCDTO(PayloadOneEntity payloadOneEntity, PayloadTwoEntity payloadTwoEntity) {
        voltageSolarPanel1 = payloadOneEntity.getC1();
        voltageSolarPanel2 = payloadOneEntity.getC2();
        voltageSolarPanel3 = payloadOneEntity.getC3();
        mode = payloadOneEntity.getC4();
        wdResetCount = payloadOneEntity.getC6();
        sBandAmpTemp = payloadOneEntity.getC10();
        mainBusVoltage = payloadOneEntity.getC11();
        amsatSwitchCurrent = payloadTwoEntity.getC4();
        momentumWheelVoltage = payloadTwoEntity.getC5();
        momentumWheelCurrent = payloadTwoEntity.getC6();
        momentumWheelSpeed = payloadTwoEntity.getC7();
        tankPressure = payloadTwoEntity.getC8();
        temperatureSolarPanel1 = payloadTwoEntity.getC9();
        temperatureBattery1 = payloadTwoEntity.getC10();
    }

    public Long getVoltageSolarPanel1() {
        return voltageSolarPanel1;
    }

    public Long getVoltageSolarPanel2() {
        return voltageSolarPanel2;
    }

    public Long getVoltageSolarPanel3() {
        return voltageSolarPanel3;
    }

    public String getMode() {
        return mode;
    }

    public Long getWdResetCount() {
        return wdResetCount;
    }

    public Long getsBandAmpTemp() {
        return sBandAmpTemp;
    }

    public Long getMainBusVoltage() {
        return mainBusVoltage;
    }

    public Long getAmsatSwitchCurrent() {
        return amsatSwitchCurrent;
    }

    public Long getMomentumWheelVoltage() {
        return momentumWheelVoltage;
    }

    public Long getMomentumWheelCurrent() {
        return momentumWheelCurrent;
    }

    public Long getMomentumWheelSpeed() {
        return momentumWheelSpeed;
    }

    public Long getTankPressure() {
        return tankPressure;
    }

    public Long getTemperatureSolarPanel1() {
        return temperatureSolarPanel1;
    }

    public Long getTemperatureBattery1() {
        return temperatureBattery1;
    }
}
