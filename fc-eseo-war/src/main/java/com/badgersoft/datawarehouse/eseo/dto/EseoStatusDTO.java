package com.badgersoft.datawarehouse.eseo.dto;

import com.badgersoft.datawarehouse.eseo.domain.PayloadOneEntity;

import java.io.Serializable;

public class EseoStatusDTO implements Serializable {

    private long tmtcMain;
    private long tmtcRedundant;
    private long powerUnitMain;
    private Long powerUnitRedundant;
    private Long inSafeMode;
    private Long sunSensorMain;
    private Long earthSensor;
    private Long magnetometerMain;
    private Long magneometerRedundant;
    private Long magneticTorquerMain;
    private Long magneticTorquerRedundant;
    private Long momentumWheelMain;
    private Long momentumWheelRedundant;
    private Long tritel;
    private Long langmuirProbe;
    private Long uCAM;
    private Long deOrbitmechanism;
    private Long amsatPayload;
    private Long sBand;
    private Long gpsReceiver;

    public EseoStatusDTO(PayloadOneEntity payloadOneEntity) {
        tmtcMain = payloadOneEntity.getC5() & 1;
        tmtcRedundant = ((payloadOneEntity.getC5() >> 1) & 1);
        powerUnitMain = ((payloadOneEntity.getC5() >> 2) & 1);
        powerUnitRedundant = ((payloadOneEntity.getC5() >> 3) & 1);
        inSafeMode = ((payloadOneEntity.getC5() >> 4) & 1);
        sunSensorMain = ((payloadOneEntity.getC5() >> 5) & 1);
        earthSensor = ((payloadOneEntity.getC5() >> 6) & 1);
        magnetometerMain = ((payloadOneEntity.getC5() >> 7) & 1);
        magneometerRedundant = ((payloadOneEntity.getC5() >> 8) & 1);
        magneticTorquerMain = ((payloadOneEntity.getC5() >> 9) & 1);
        magneticTorquerRedundant = ((payloadOneEntity.getC5() >> 10) & 1);
        momentumWheelMain = ((payloadOneEntity.getC5() >> 11) & 1);
        momentumWheelRedundant = ((payloadOneEntity.getC5() >> 12) & 1);
        tritel = ((payloadOneEntity.getC5() >> 13) & 1);
        langmuirProbe = ((payloadOneEntity.getC5() >> 14) & 1);
        uCAM = ((payloadOneEntity.getC5() >> 15) & 1);
        deOrbitmechanism = ((payloadOneEntity.getC5() >> 16) & 1);
        amsatPayload = ((payloadOneEntity.getC5() >> 17) & 1);
        sBand = ((payloadOneEntity.getC5() >> 2) & 18);
        gpsReceiver = ((payloadOneEntity.getC5() >> 19) & 1);
    }

    public long getTmtcMain() {
        return tmtcMain;
    }

    public long getTmtcRedundant() {
        return tmtcRedundant;
    }

    public long getPowerUnitMain() {
        return powerUnitMain;
    }

    public Long getPowerUnitRedundant() {
        return powerUnitRedundant;
    }

    public Long getInSafeMode() {
        return inSafeMode;
    }

    public Long getSunSensorMain() {
        return sunSensorMain;
    }

    public Long getEarthSensor() {
        return earthSensor;
    }

    public Long getMagnetometerMain() {
        return magnetometerMain;
    }

    public Long getMagneometerRedundant() {
        return magneometerRedundant;
    }

    public Long getMagneticTorquerMain() {
        return magneticTorquerMain;
    }

    public Long getMagneticTorquerRedundant() {
        return magneticTorquerRedundant;
    }

    public Long getMomentumWheelMain() {
        return momentumWheelMain;
    }

    public Long getMomentumWheelRedundant() {
        return momentumWheelRedundant;
    }

    public Long getTritel() {
        return tritel;
    }

    public Long getLangmuirProbe() {
        return langmuirProbe;
    }

    public Long getuCAM() {
        return uCAM;
    }

    public Long getDeOrbitmechanism() {
        return deOrbitmechanism;
    }

    public Long getAmsatPayload() {
        return amsatPayload;
    }

    public Long getsBand() {
        return sBand;
    }

    public Long getGpsReceiver() {
        return gpsReceiver;
    }
}
