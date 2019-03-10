package com.badgersoft.datawarehouse.funcube.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by davidjohnson on 25/09/2016.
 */
@Entity
@Table(name = "whole_orbit_data")
public class WholeOrbitDataEntity extends TelemetryEntity {

    static {
        setupSunSensors();
        PA_TEMPS_256 = buildLookupTable(256);
        PA_TEMPS_1024 = buildLookupTable(1024);
    }


    @Id
    private long id;

    @Column(name = "sequence_number")
    private long sequenceNumber;

    @Column(name = "satellite_time")
    private Date satelliteTime;


    private double blackChassis;
    private double silverChassis;
    private double blackPanel;
    private double silverPanel;
    private double solPanelXplus;
    private double solPanelXminus;
    private double solPanelYplus;
    private double solPanelYminus;
    private double solPanelXvolts;
    private double solPanelYvolts;
    private double solPanelZvolts;
    private double totPhotoCurr;
    private double batteryVolts;
    private double totSystemCurr;

    public WholeOrbitDataEntity() {}

    public WholeOrbitDataEntity(long id, Long sequenceNumber, Date satelliteTime, String binaryString) {
        this.id = id;
        this.sequenceNumber = sequenceNumber;
        this.satelliteTime = satelliteTime;

        blackChassis = scale(getBitsAsULong(12, binaryString), -0.024, 75.244);
        silverChassis = scale(getBitsAsULong(12, binaryString), -0.024, 74.750);
        blackPanel = scale(getBitsAsULong(12, binaryString), -0.024, 75.039);
        silverPanel = scale(getBitsAsULong(12, binaryString), -0.024, 75.987);
        solPanelXplus = scale(getBitsAsULong(10, binaryString), -0.2073, 158.239);
        solPanelXminus = scale(getBitsAsULong(10, binaryString), -0.2073, 158.227);
        solPanelYplus = scale(getBitsAsULong(10, binaryString), -0.2076, 158.656);
        solPanelYminus = scale(getBitsAsULong(10, binaryString), -0.2087, 159.045);
        solPanelXvolts = scale(getBitsAsULong(16, binaryString), 0.001, 0.0);
        solPanelYvolts = scale(getBitsAsULong(16, binaryString), 0.001, 0.0);
        solPanelZvolts = scale(getBitsAsULong(16, binaryString), 0.001, 0.0);
        totPhotoCurr = scale(getBitsAsULong(16, binaryString), 0.01, 0.0);
        batteryVolts = scale(getBitsAsULong(16, binaryString), 0.001, 0.0);
        totSystemCurr = scale(getBitsAsULong(16, binaryString), 0.01, 0.0);

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Date getSatelliteTime() {
        return satelliteTime;
    }

    public void setSatelliteTime(Date satelliteTime) {
        this.satelliteTime = satelliteTime;
    }

    public double getBlackChassis() {
        return blackChassis;
    }

    public void setBlackChassis(double blackChassis) {
        this.blackChassis = blackChassis;
    }

    public double getSilverChassis() {
        return silverChassis;
    }

    public void setSilverChassis(double silverChassis) {
        this.silverChassis = silverChassis;
    }

    public double getBlackPanel() {
        return blackPanel;
    }

    public void setBlackPanel(double blackPanel) {
        this.blackPanel = blackPanel;
    }

    public double getSilverPanel() {
        return silverPanel;
    }

    public void setSilverPanel(double silverPanel) {
        this.silverPanel = silverPanel;
    }

    public double getSolPanelXplus() {
        return solPanelXplus;
    }

    public void setSolPanelXplus(double solPanelXplus) {
        this.solPanelXplus = solPanelXplus;
    }

    public double getSolPanelXminus() {
        return solPanelXminus;
    }

    public void setSolPanelXminus(double solPanelXminus) {
        this.solPanelXminus = solPanelXminus;
    }

    public double getSolPanelYplus() {
        return solPanelYplus;
    }

    public void setSolPanelYplus(double solPanelYplus) {
        this.solPanelYplus = solPanelYplus;
    }

    public double getSolPanelYminus() {
        return solPanelYminus;
    }

    public void setSolPanelYminus(double solPanelYminus) {
        this.solPanelYminus = solPanelYminus;
    }

    public double getSolPanelXvolts() {
        return solPanelXvolts;
    }

    public void setSolPanelXvolts(double solPanelXvolts) {
        this.solPanelXvolts = solPanelXvolts;
    }

    public double getSolPanelYvolts() {
        return solPanelYvolts;
    }

    public void setSolPanelYvolts(double solPanelYvolts) {
        this.solPanelYvolts = solPanelYvolts;
    }

    public double getSolPanelZvolts() {
        return solPanelZvolts;
    }

    public void setSolPanelZvolts(double solPanelZvolts) {
        this.solPanelZvolts = solPanelZvolts;
    }

    public double getTotPhotoCurr() {
        return totPhotoCurr;
    }

    public void setTotPhotoCurr(double totPhotoCurr) {
        this.totPhotoCurr = totPhotoCurr;
    }

    public double getBatteryVolts() {
        return batteryVolts;
    }

    public void setBatteryVolts(double batteryVolts) {
        this.batteryVolts = batteryVolts;
    }

    public double getTotSystemCurr() {
        return totSystemCurr;
    }

    public void setTotSystemCurr(double totSystemCurr) {
        this.totSystemCurr = totSystemCurr;
    }

    @Transient
    private double calcMcuTemp(long adc)
    {
        double vt = adc * 0.00078753; //Convert the ADC reading into voltage

        if (vt >= 0.7012) // Check for Hot or Cold Slope
        {
            return 25.0 - ((vt - 0.7012) / 0.001646); // Cold Slope)
        }
        else
        {
            return 25.0 - ((vt - 0.7012) / 0.001749); // Hot Slope
        }
    }

}
