package com.badgersoft.datawarehouse.eseo.dto;

import com.badgersoft.datawarehouse.eseo.domain.PayloadOneEntity;
import com.badgersoft.datawarehouse.eseo.domain.PayloadTwoEntity;

import java.io.Serializable;

public class EseoOBCDTO implements Serializable {

    private String voltageSolarPanel1;
    private String voltageSolarPanel2;
    private String voltageSolarPanel3;
    private String mode;
    private Long wdResetCount;
    private String sBandAmpTemp;
    private String mainBusVoltage;
    private String amsatSwitchCurrent;
    private String momentumWheelVoltage;
    private String momentumWheelCurrent;
    private String momentumWheelSpeed;
    private String tankPressure;
    private String temperatureSolarPanel1;
    private String temperatureBattery1;

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
        voltageSolarPanel1 = payloadOneEntity.getC1() + " mV";
        voltageSolarPanel2 = payloadOneEntity.getC2() + " mV";
        voltageSolarPanel3 = payloadOneEntity.getC3() + " mV";
        mode = payloadOneEntity.getC4();
        wdResetCount = payloadOneEntity.getC6();
        sBandAmpTemp = payloadOneEntity.getC10() + " C";
        mainBusVoltage = payloadOneEntity.getC11() + " mV";
        amsatSwitchCurrent = payloadTwoEntity.getC4() + " mA";
        momentumWheelVoltage = payloadTwoEntity.getC5() + " mV";
        momentumWheelCurrent = payloadTwoEntity.getC6() + " mA";
        momentumWheelSpeed = payloadTwoEntity.getC7() + " rpm";
        tankPressure = payloadTwoEntity.getC8() + " kPa";
        temperatureSolarPanel1 = payloadTwoEntity.getC9() + " C";
        temperatureBattery1 = payloadTwoEntity.getC10() + " C";
    }

    public String getVoltageSolarPanel1() {
        return voltageSolarPanel1;
    }

    public String getVoltageSolarPanel2() {
        return voltageSolarPanel2;
    }

    public String getVoltageSolarPanel3() {
        return voltageSolarPanel3;
    }

    public String getMode() {
        return mode;
    }

    public Long getWdResetCount() {
        return wdResetCount;
    }

    public String getsBandAmpTemp() {
        return sBandAmpTemp;
    }

    public String getMainBusVoltage() {
        return mainBusVoltage;
    }

    public String getAmsatSwitchCurrent() {
        return amsatSwitchCurrent;
    }

    public String getMomentumWheelVoltage() {
        return momentumWheelVoltage;
    }

    public String getMomentumWheelCurrent() {
        return momentumWheelCurrent;
    }

    public String getMomentumWheelSpeed() {
        return momentumWheelSpeed;
    }

    public String getTankPressure() {
        return tankPressure;
    }

    public String getTemperatureSolarPanel1() {
        return temperatureSolarPanel1;
    }

    public String getTemperatureBattery1() {
        return temperatureBattery1;
    }
}
