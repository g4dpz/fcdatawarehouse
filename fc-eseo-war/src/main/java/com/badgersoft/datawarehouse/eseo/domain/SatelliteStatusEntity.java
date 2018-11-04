package com.badgersoft.datawarehouse.eseo.domain;

/**
 * Created by davidjohnson on 25/09/2016.
 */
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "satellite_status")
public class SatelliteStatusEntity {

    static final SimpleDateFormat SDTF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");

    @Id
    private Long satelliteId;

    private Long sequenceNumberOne;
    private Long sequenceNumberTwo;
    private Long frameTypeOne;
    private Long frameTypeTwo;
    private Boolean eclipseModeForced;
    private Boolean eclipsed;
    private Timestamp lastUpdated;
    private Timestamp lastUpdatedRealtimeTwo;
    private Double eclipseDepth;
    private Boolean eclipseSwitch;
    private Timestamp lastWodDump;
    private Timestamp lastResetNotification;
    private Timestamp lastNoShowNotification;
    private Long epochSequenceNumber;
    private Timestamp epochReferenceTime;

    public SatelliteStatusEntity() {
    }

    public final Long getSatelliteId() {
        return satelliteId;
    }

    public final void setSatelliteId(Long satelliteId) {
        this.satelliteId = satelliteId;
    }

    public final Long getSequenceNumberOne() {
        return sequenceNumberOne;
    }

    public final void setSequenceNumberOne(Long sequenceNumberOne) {
        this.sequenceNumberOne = sequenceNumberOne;
    }

    public final Long getSequenceNumberTwo() {
        return sequenceNumberTwo;
    }

    public final void setSequenceNumberTwo(Long sequenceNumberTwo) {
        this.sequenceNumberTwo = sequenceNumberTwo;
    }

    public final Boolean isEclipseModeForced() {
        return eclipseModeForced;
    }

    public final void setEclipseModeForced(Boolean eclipseModeForced) {
        this.eclipseModeForced = eclipseModeForced;
    }

    public final Boolean isEclipsed() {
        return eclipsed;
    }

    public final void setEclipsed(Boolean eclipsed) {
        this.eclipsed = eclipsed;
    }

    public final Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public final void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public final Timestamp getLastUpdatedRealtimeTwo() {
        return lastUpdatedRealtimeTwo;
    }

    public final void setLastUpdatedRealtimeTwo(Timestamp lastUpdatedRealtimeTwo) {
        this.lastUpdatedRealtimeTwo = lastUpdatedRealtimeTwo;
    }

    public final Double getEclipseDepth() {
        return eclipseDepth;
    }

    public final void setEclipseDepth(Double eclipseDepth) {
        this.eclipseDepth = eclipseDepth;
    }

    public void setEclipseSwitch(Boolean eclipseSwitch) {
        this.eclipseSwitch = eclipseSwitch;
    }

    public Boolean getEclipseModeForced() {
        return eclipseModeForced;
    }

    public Boolean getEclipsed() {
        return eclipsed;
    }

    public Boolean isEclipseSwitch() {
        return eclipseSwitch;
    }

    public Long getEpochSequenceNumber() {
        return epochSequenceNumber;
    }

    public void setEpochSequenceNumber(Long epochSequenceNumber) {
        this.epochSequenceNumber = epochSequenceNumber;
    }

    public Timestamp getEpochReferenceTime() {
        return epochReferenceTime;
    }

    public void setEpochReferenceTime(Timestamp epochReferenceTime) {
        this.epochReferenceTime = epochReferenceTime;
    }

    @Transient
    public String getMode() {
        if (isEclipseSwitch()) {
            return "Auto";
        }
        else {
            return "Manual";
        }
    }

    @Transient
    public String getTransponderState() {
        if (!isEclipseSwitch()) {
            if (!isEclipseModeForced()) {
                return "Off";
            }
            else {
                return "On";
            }
        }
        else {
            if (!isEclipseModeForced()) {
                if (!isEclipsed()) {
                    return "Off";
                }
                else {
                    return "On";
                }
            }
            else {
                if (!isEclipsed()) {
                    return "Off";
                }
                else {
                    return "On";
                }
            }
        }
    }

    public final Timestamp getLastWodDump() {
        return lastWodDump;
    }

    public final void setLastWodDump(Timestamp lastWodDump) {
        this.lastWodDump = lastWodDump;
    }

    public final Timestamp getLastResetNotification() {
        return lastResetNotification;
    }

    public final void setLastResetNotification(Timestamp lastResetNotification) {
        this.lastResetNotification = lastResetNotification;
    }

    public final Timestamp getLastNoShowNotification() {
        return lastNoShowNotification;
    }

    public final void setLastNoShowNotification(Timestamp lastNoShowNotification) {
        this.lastNoShowNotification = lastNoShowNotification;
    }

    public Long getFrameTypeOne() {
        return frameTypeOne;
    }

    public void setFrameTypeOne(Long frameTypeOne) {
        this.frameTypeOne = frameTypeOne;
    }

    public Long getFrameTypeTwo() {
        return frameTypeTwo;
    }

    public void setFrameTypeTwo(Long frameTypeTwo) {
        this.frameTypeTwo = frameTypeTwo;
    }

    @Override
    public String toString() {
        return "SatelliteStatusEntity{" +
                "satelliteId=" + satelliteId +
                ", sequenceNumberOne=" + sequenceNumberOne +
                ", sequenceNumberTwo=" + sequenceNumberTwo +
                ", frameTypeOne=" + frameTypeOne +
                ", frameTypeTwo=" + frameTypeTwo +
                ", eclipseModeForced=" + eclipseModeForced +
                ", eclipsed=" + eclipsed +
                ", lastUpdated=" + lastUpdated +
                ", lastUpdatedRealtimeTwo=" + lastUpdatedRealtimeTwo +
                ", eclipseDepth=" + eclipseDepth +
                ", eclipseSwitch=" + eclipseSwitch +
                ", lastWodDump=" + lastWodDump +
                ", lastResetNotification=" + lastResetNotification +
                ", lastNoShowNotification=" + lastNoShowNotification +
                ", epochSequenceNumber=" + epochSequenceNumber +
                ", epochReferenceTime=" + epochReferenceTime +
                '}';
    }
}
