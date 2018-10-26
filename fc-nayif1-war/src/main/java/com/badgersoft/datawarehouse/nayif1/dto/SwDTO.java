package com.badgersoft.datawarehouse.nayif1.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class SwDTO implements Serializable {
    private static Logger LOG = LoggerFactory.getLogger(SwDTO.class.getName());

    private long dtmfCmdCount;
    private long dtmfLastCmd;
    private String dtmfCmdSuccess;
    private String dataValidASIB;
    private String dataValidEPS;
    private String dataValidPA;
    private String dataValidRF;
    private String dataValidiMTQ;
    private String dataValidAntsBusB;
    private String dataValidAntsBusA;
    private String inEclipseMode;
    private String inSafeMode;
    private String hardwareABFOnOff;
    private String softwareABFOnOff;
    private String deploymentWait;

    public SwDTO() {}

    public SwDTO(long dtmfCmdCount, long dtmfLastCmd, String dtmfCmdSuccess, String dataValidASIB, String dataValidEPS, String dataValidPA, String dataValidRF, String dataValidiMTQ, String dataValidAntsBusB, String dataValidAntsBusA, String inEclipseMode, String inSafeMode, String hardwareABFOnOff, String softwareABFOnOff, String deploymentWait) {
        this.dtmfCmdCount = dtmfCmdCount;
        this.dtmfLastCmd = dtmfLastCmd;
        this.dtmfCmdSuccess = dtmfCmdSuccess;
        this.dataValidASIB = dataValidASIB;
        this.dataValidEPS = dataValidEPS;
        this.dataValidPA = dataValidPA;
        this.dataValidRF = dataValidRF;
        this.dataValidiMTQ = dataValidiMTQ;
        this.dataValidAntsBusB = dataValidAntsBusB;
        this.dataValidAntsBusA = dataValidAntsBusA;
        this.inEclipseMode = inEclipseMode;
        this.inSafeMode = inSafeMode;
        this.hardwareABFOnOff = hardwareABFOnOff;
        this.softwareABFOnOff = softwareABFOnOff;
        this.deploymentWait = deploymentWait;
    }

    public long getDtmfCmdCount() {
        return dtmfCmdCount;
    }

    public String getDtmfLastCmd() {

        switch((int) dtmfLastCmd) {
            case 2: return "CPLD set power on mode";
            case 4: return "CPLD change mode";

            case 8: return "Extended Command Start";
            case 9: return "Data collect state";
            case 10: return "Data collect state";
            case 11: return "Ants Arm";
            case 12: return "Ants Deploy";
            case 13: return "Restart MCU";

            case 17: return "Fitter Save";
            case 18: return "Fitter Copy";
            case 19: return "Debug Read Mem";
            case 20: return "Debug Read Fram";
            case 21: return "Debug Write Fram";
            case 22: return "Debug I2c Command";
            case 23: return "Update Idle Time";
            case 24: return "Update Call Sign";
            case 25: return "Update Eclipse Mode";
            case 26: return "Update Safe Mode";
            case 27: return "Eps Set PPT Volt";
            case 28: return "Eps Reset";
            case 29: return "Set Auto Flags";
            case 30: return "PreFlight";
            case 31: return "EpsSetPvAuto";
            default : return "n/a";
        }
    }

    public String getDtmfCmdSuccess() {
        return dtmfCmdSuccess;
    }

    public String getDataValidASIB() {
        return dataValidASIB;
    }

    public String getDataValidEPS() {
        return dataValidEPS;
    }

    public String getDataValidPA() {
        return dataValidPA;
    }

    public String getDataValidRF() {
        return dataValidRF;
    }

    public String getDataValidiMTQ() {
        return dataValidiMTQ;
    }

    public String getDataValidAntsBusB() {
        return dataValidAntsBusB;
    }

    public String getDataValidAntsBusA() {
        return dataValidAntsBusA;
    }

    public String getInEclipseMode() {
        return inEclipseMode;
    }

    public String getInSafeMode() {
        return inSafeMode;
    }

    public String getHardwareABFOnOff() {
        return hardwareABFOnOff;
    }

    public String getSoftwareABFOnOff() {
        return softwareABFOnOff;
    }

    public String getDeploymentWait() {
        return deploymentWait;
    }
}
