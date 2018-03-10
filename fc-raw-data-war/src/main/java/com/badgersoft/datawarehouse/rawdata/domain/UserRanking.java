package com.badgersoft.datawarehouse.rawdata.domain;

import java.sql.Timestamp;
import java.util.Date;


import javax.persistence.*;

@Entity
@Table(name = "user_ranking")
public class UserRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "satellite_id")
    private Long satelliteId;
    @Column(name = "site_id")
    private String siteId;
    private Long number;
    @Column(name = "latest_upload_date")
    private Timestamp latestUploadDate;
    @Column(name = "site_alias")
    private String siteAlias;
    @Column(name = "first_upload_date")
    private Timestamp firstUploadDate;

    public UserRanking() {

    }

    public UserRanking(final Long satelliteId, final String siteId, final Long number,
                             final Timestamp latestUploadDate, final Timestamp firstUploadDate) {
        super();
        this.satelliteId = satelliteId;
        this.siteId = siteId;
        this.number = number;
        this.latestUploadDate = latestUploadDate;
        this.firstUploadDate = firstUploadDate;
    }

    public final Long getId() {
        return id;
    }

    public final void setId(Long id) {
        this.id = id;
    }

    public final void setSatelliteId(Long satelliteId) {
        this.satelliteId = satelliteId;
    }

    public final void setNumber(Long number) {
        this.number = number;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public final long getSatelliteId() {
        return satelliteId;
    }

    public final void setSatelliteId(long satelliteId) {
        this.satelliteId = satelliteId;
    }

    public final Date getLatestUploadDate() {
        return latestUploadDate;
    }

    public final void setLatestUploadDate(Timestamp latestUploadDate) {
        this.latestUploadDate = latestUploadDate;
    }

    public final String getSiteAlias() {
        return siteAlias;
    }

    public final void setSiteAlias(String siteAlias) {
        this.siteAlias = siteAlias;
    }

    public final Timestamp getFirstUploadDate() {
        return firstUploadDate;
    }

    public final void setFirstUploadDate(Timestamp firstUploadDate) {
        this.firstUploadDate = firstUploadDate;
    }

}
