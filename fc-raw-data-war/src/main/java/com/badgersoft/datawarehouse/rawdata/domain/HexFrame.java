// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package com.badgersoft.datawarehouse.rawdata.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "HexFrame")
public class HexFrame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "HexFrameUser",
            joinColumns = @JoinColumn(name = "hexFrameId"),
            inverseJoinColumns = @JoinColumn(name = "userId"))
    private Set<User> users = new HashSet<User>();

    private Long satelliteId;
    private Long sequenceNumber;
    private String hexString;
    private Date createdDate;
    private boolean valid;
    private Long frameType;
    private boolean wodProcessed;
    private boolean fitterProcessed;
    private boolean highPrecisionProcessed;
    private boolean realtimeProcessed;
    private Timestamp satelliteTime;

    private String eclipsed;

    private String eclipseDepth;

    private String latitude;

    private String longitude;

    private Boolean outOfOrder;

    private String digest;

    public HexFrame() {
    }

    public HexFrame(
            final Long satelliteId, final Long frameType, final Long sequenceNumber,
            final String hexString, final Date createdDate, final boolean valid,
            final Timestamp satelliteTime) {
        this.satelliteId = satelliteId;
        this.frameType = frameType;
        this.sequenceNumber = sequenceNumber;
        this.hexString = hexString;
        this.createdDate = createdDate;
        this.valid = valid;
        this.satelliteTime = satelliteTime;
        this.outOfOrder = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Long getSatelliteId() {
        return satelliteId;
    }

    public void setSatelliteId(Long satelliteId) {
        this.satelliteId = satelliteId;
    }

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getHexString() {
        return hexString;
    }

    public void setHexString(String hexString) {
        this.hexString = hexString;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Long getFrameType() {
        return frameType;
    }

    public void setFrameType(Long frameType) {
        this.frameType = frameType;
    }

    public boolean isWodProcessed() {
        return wodProcessed;
    }

    public void setWodProcessed(boolean wodProcessed) {
        this.wodProcessed = wodProcessed;
    }

    public boolean isFitterProcessed() {
        return fitterProcessed;
    }

    public void setFitterProcessed(boolean fitterProcessed) {
        this.fitterProcessed = fitterProcessed;
    }

    public boolean isHighPrecisionProcessed() {
        return highPrecisionProcessed;
    }

    public void setHighPrecisionProcessed(boolean highPrecisionProcessed) {
        this.highPrecisionProcessed = highPrecisionProcessed;
    }

    public final Timestamp getSatelliteTime() {
        return satelliteTime;
    }

    public final void setSatelliteTime(Timestamp satelliteTime) {
        this.satelliteTime = satelliteTime;
    }

    /**
     * @param eclipsed
     */
    public void setEclipsed(String eclipsed) {
        this.eclipsed = eclipsed;
    }

    /**
     * @param eclipseDepth
     */
    public void setEclipseDepth(String eclipseDepth) {
        this.eclipseDepth = eclipseDepth;
    }

    /**
     * @param latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @param longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public final String getEclipsed() {
        return eclipsed;
    }

    public final String getEclipseDepth() {
        return eclipseDepth;
    }

    public final String getLatitude() {
        return latitude;
    }

    public final String getLongitude() {
        return longitude;
    }

    public final Boolean isOutOfOrder() {
        return outOfOrder;
    }

    public final void setOutOfOrder(Boolean outOfOrder) {
        this.outOfOrder = outOfOrder;
    }

    public void setDigest(final String digest) {
        this.digest = digest;
    }

    public final String getDigest() {
        return digest;
    }

    public final boolean isRealtimeProcessed() {
        return realtimeProcessed;
    }

    public final void setRealtimeProcessed(boolean realtimeProcessed) {
        this.realtimeProcessed = realtimeProcessed;
    }

}
