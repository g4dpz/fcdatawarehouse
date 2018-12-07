package com.badgersoft.datawarehouse.eseo.dto;

import com.badgersoft.datawarehouse.eseo.domain.PayloadOneEntity;

import java.io.Serializable;

public class EseoStatusDTO implements Serializable {

    private String tmtcMain;
    private String tmtcRedundant;
    private String powerUnitMain;
    private String powerUnitRedundant;
    private String inSafeMode;
    private String sunSensorMain;
    private String earthSensor;
    private String magnetometerMain;
    private String magneometerRedundant;
    private String magneticTorquerMain;
    private String magneticTorquerRedundant;
    private String momentumWheelMain;
    private String momentumWheelRedundant;
    private String tritel;
    private String langmuirProbe;
    private String uCAM;
    private String deOrbitmechanism;
    private String amsatPayload;
    private String sBand;
    private String gpsReceiver;

    public EseoStatusDTO(PayloadOneEntity payloadOneEntity) {
        if (payloadOneEntity == null) {
            tmtcMain = "N/A";
            tmtcRedundant = "N/A";
            powerUnitMain = "N/A";
            powerUnitRedundant = "N/A";
            inSafeMode = "N/A";
            sunSensorMain = "N/A";
            earthSensor = "N/A";
            magnetometerMain = "N/A";
            magneometerRedundant = "N/A";
            magneticTorquerMain = "N/A";
            magneticTorquerRedundant = "N/A";
            momentumWheelMain = "N/A";
            momentumWheelRedundant = "N/A";
            tritel =  "N/A";
            langmuirProbe =  "N/A";
            uCAM =  "N/A";
            deOrbitmechanism =  "N/A";
            amsatPayload =  "N/A";
            sBand =  "N/A";
            gpsReceiver =  "N/A";
        }
        else {
            tmtcMain = ((payloadOneEntity.getC5() & 1) == 1) ? "ON" : "OFF";
            tmtcRedundant = (((payloadOneEntity.getC5() >> 1) & 1) == 1) ? "ON" : "OFF";
            powerUnitMain = (((payloadOneEntity.getC5() >> 2) & 1) == 1) ? "ON" : "OFF";
            powerUnitRedundant = (((payloadOneEntity.getC5() >> 3) & 1) == 1) ? "ON" : "OFF";
            inSafeMode = (((payloadOneEntity.getC5() >> 4) & 1) == 1) ? "ON" : "OFF";
            sunSensorMain = (((payloadOneEntity.getC5() >> 5) & 1) == 1) ? "ON" : "OFF";
            earthSensor = (((payloadOneEntity.getC5() >> 6) & 1) == 1) ? "ON" : "OFF";
            magnetometerMain = (((payloadOneEntity.getC5() >> 7) & 1) == 1) ? "ON" : "OFF";
            magneometerRedundant = (((payloadOneEntity.getC5() >> 8) & 1) == 1) ? "ON" : "OFF";
            magneticTorquerMain = (((payloadOneEntity.getC5() >> 9) & 1) == 1) ? "ON" : "OFF";
            magneticTorquerRedundant = (((payloadOneEntity.getC5() >> 10) & 1) == 1) ? "ON" : "OFF";
            momentumWheelMain = (((payloadOneEntity.getC5() >> 11) & 1) == 1) ? "ON" : "OFF";
            momentumWheelRedundant = (((payloadOneEntity.getC5() >> 12) & 1) == 1) ? "ON" : "OFF";
            tritel = (((payloadOneEntity.getC5() >> 13) & 1) == 1) ? "ON" : "OFF";
            langmuirProbe = (((payloadOneEntity.getC5() >> 14) & 1) == 1) ? "ON" : "OFF";
            uCAM = (((payloadOneEntity.getC5() >> 15) & 1) == 1) ? "ON" : "OFF";
            deOrbitmechanism = (((payloadOneEntity.getC5() >> 16) & 1) == 1) ? "ON" : "OFF";
            amsatPayload = (((payloadOneEntity.getC5() >> 17) & 1) == 1) ? "ON" : "OFF";
            sBand = (((payloadOneEntity.getC5() >> 2) & 18) == 1) ? "ON" : "OFF";
            gpsReceiver = (((payloadOneEntity.getC5() >> 19) & 1) == 1) ? "ON" : "OFF";
        }
    }

    public String getTmtcMain() {
        return tmtcMain;
    }

    public String getTmtcRedundant() {
        return tmtcRedundant;
    }

    public String getPowerUnitMain() {
        return powerUnitMain;
    }

    public String getPowerUnitRedundant() {
        return powerUnitRedundant;
    }

    public String getInSafeMode() {
        return inSafeMode;
    }

    public String getSunSensorMain() {
        return sunSensorMain;
    }

    public String getEarthSensor() {
        return earthSensor;
    }

    public String getMagnetometerMain() {
        return magnetometerMain;
    }

    public String getMagneometerRedundant() {
        return magneometerRedundant;
    }

    public String getMagneticTorquerMain() {
        return magneticTorquerMain;
    }

    public String getMagneticTorquerRedundant() {
        return magneticTorquerRedundant;
    }

    public String getMomentumWheelMain() {
        return momentumWheelMain;
    }

    public String getMomentumWheelRedundant() {
        return momentumWheelRedundant;
    }

    public String getTritel() {
        return tritel;
    }

    public String getLangmuirProbe() {
        return langmuirProbe;
    }

    public String getuCAM() {
        return uCAM;
    }

    public String getDeOrbitmechanism() {
        return deOrbitmechanism;
    }

    public String getAmsatPayload() {
        return amsatPayload;
    }

    public String getsBand() {
        return sBand;
    }

    public String getGpsReceiver() {
        return gpsReceiver;
    }
}
