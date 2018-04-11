package com.badgersoft.datawarehouse.jy1sat.domain;

/**
 * Created by davidjohnson on 25/09/2016.
 */
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "satellite_status")
public class SatelliteStatusEntity {

    @Id
    private Long satelliteId;

    private Long sequenceNumber;
    private Boolean eclipseModeForced;
    private Boolean eclipsed;
    private Date lastUpdated;
    private Double eclipseDepth;
    private Boolean eclipseSwitch;
    private Date lastWodDump;
    private Date lastResetNotification;
    private Date lastNoShowNotification;
    private Long epochSequenceNumber;
    private Date epochReferenceTime;
    private Long packetCount;
    private String latitude;
    private String longitude;
    private String contributors;
    private Date lastWodTime;
    private Date lastHighresTime;
    private Date lastRealtimeTime;
    private Date lastFitterTime;
    private Long frameType;

    public SatelliteStatusEntity() {
    }

    public final Long getSatelliteId() {
        return satelliteId;
    }

    public final void setSatelliteId(Long satelliteId) {
        this.satelliteId = satelliteId;
    }

    public final Long getSequenceNumber() {
        return sequenceNumber;
    }

    public final void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
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

    public final Date getLastUpdated() {
        return lastUpdated;
    }

    public final void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
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

    public Date getEpochReferenceTime() {
        return epochReferenceTime;
    }

    public void setEpochReferenceTime(Date epochReferenceTime) {
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

    public final Date getLastWodDump() {
        return lastWodDump;
    }

    public final void setLastWodDump(Date lastWodDump) {
        this.lastWodDump = lastWodDump;
    }

    public final Date getLastResetNotification() {
        return lastResetNotification;
    }

    public final void setLastResetNotification(Date lastResetNotification) {
        this.lastResetNotification = lastResetNotification;
    }

    public final Date getLastNoShowNotification() {
        return lastNoShowNotification;
    }

    public final void setLastNoShowNotification(Date lastNoShowNotification) {
        this.lastNoShowNotification = lastNoShowNotification;
    }

    public Long getPacketCount() {
        return packetCount;
    }

    public void setPacketCount(Long packetCount) {
        this.packetCount = packetCount;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getContributors() {
        return contributors;
    }

    public void setContributors(String contributors) {
        this.contributors = contributors;
    }

    public Date getLastWodTime() {
        return lastWodTime;
    }

    public void setLastWodTime(Date lastWodTime) {
        this.lastWodTime = lastWodTime;
    }

    public Date getLastHighresTime() {
        return lastHighresTime;
    }

    public void setLastHighresTime(Date lastHighresTime) {
        this.lastHighresTime = lastHighresTime;
    }

    public Date getLastRealtimeTime() {
        return lastRealtimeTime;
    }

    public void setLastRealtimeTime(Date lastRealtimeTime) {
        this.lastRealtimeTime = lastRealtimeTime;
    }

    public Date getLastFitterTime() {
        return lastFitterTime;
    }

    public void setLastFitterTime(Date lastFitterTime) {
        this.lastFitterTime = lastFitterTime;
    }

    public Long getFrameType() {
        return frameType;
    }

    public void setFrameType(Long frameType) {
        this.frameType = frameType;
    }

    @Override
    public String toString() {
        return "SatelliteStatusEntity{" +
                "satelliteId=" + satelliteId +
                ", sequenceNumber=" + sequenceNumber +
                ", eclipseModeForced=" + eclipseModeForced +
                ", eclipsed=" + eclipsed +
                ", lastUpdated=" + lastUpdated +
                ", eclipseDepth=" + eclipseDepth +
                ", eclipseSwitch=" + eclipseSwitch +
                ", lastWodDump=" + lastWodDump +
                ", lastResetNotification=" + lastResetNotification +
                ", lastNoShowNotification=" + lastNoShowNotification +
                ", epochSequenceNumber=" + epochSequenceNumber +
                ", epochReferenceTime=" + epochReferenceTime +
                ", packetCount=" + packetCount +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", contributors='" + contributors + '\'' +
                ", lastWodTime=" + lastWodTime +
                ", lastHighresTime=" + lastHighresTime +
                ", lastRealtimeTime=" + lastRealtimeTime +
                ", lastFitterTime=" + lastFitterTime +
                ", frameType=" + frameType +
                '}';
    }
}
