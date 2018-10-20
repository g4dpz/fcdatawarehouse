package com.badgersoft.datawarehouse.jy1sat.domain;

/**
 * Created by davidjohnson on 25/09/2016.
 */
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "satellite_status")
public class SatelliteStatusEntity {

    @Id
    @Column(name = "satellite_id")
    private Long satelliteId;

    @Column(name = "sequence_number")
    private Long sequenceNumber;
    @Column(name = "eclipse_mode_forced")
    private Boolean eclipseModeForced;
    private Boolean eclipsed;
    @Column(name = "last_updated")
    private Date lastUpdated;
    @Column(name = "eclipse_depth")
    private Double eclipseDepth;
    @Column(name = "eclipse_switch")
    private Boolean eclipseSwitch;
    @Column(name = "last_wod_dump")
    private Date lastWodDump;
    @Column(name = "last_reset_notification")
    private Date lastResetNotification;
    @Column(name = "last_no_show_notification")
    private Date lastNoShowNotification;
    @Column(name = "epoch_sequence_number")
    private Long epochSequenceNumber;
    @Column(name = "epoch_reference_time")
    private Date epochReferenceTime;
    @Column(name = "packet_count")
    private Long packetCount;
    private String latitude;
    private String longitude;
    private String contributors;
    @Column(name = "last_wod_time")
    private Date lastWodTime;
    @Column(name = "last_highres_time")
    private Date lastHighresTime;
    @Column(name = "last_realime_time")
    private Date lastRealtimeTime;
    @Column(name = "last_fitter_time")
    private Date lastFitterTime;
    @Column(name = "frame_type")
    private Long frameType;
    @Column(name = "catalogue_number")
    private Long catalogueNumber;

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
