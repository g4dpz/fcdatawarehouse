package com.badgersoft.datawarehouse.jy1sat.domain;

/**
 * Created by davidjohnson on 25/09/2016.
 */

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "fitter_message")
public class FitterMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_text")
    private String messageText;

    @Column(name = "last_received")
    private Date lastReceived;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "satellite_id")
    private Long satelliteId;

    private Boolean debug;

    private Boolean display;

    private String slot;

    public FitterMessageEntity() {
    }

    public FitterMessageEntity(String theMessageText, Date lastReceived, Long satelliteId, Boolean debug,
                               String slot, Date createdDate) {
        super();
        this.messageText = theMessageText;
        this.lastReceived = lastReceived;
        this.satelliteId = satelliteId;
        this.debug = debug;
        this.createdDate = createdDate;
        this.display = !debug;
        this.slot = slot;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Date getLastReceived() {
        return lastReceived;
    }

    public void setLastReceived(Date lastReceived) {
        this.lastReceived = lastReceived;
    }

    public Long getSatelliteId() {
        return satelliteId;
    }

    public void setSatelliteId(Long satelliteId) {
        this.satelliteId = satelliteId;
    }

    public Boolean getDebug() {
        return debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getDisplay() {
        return display;
    }
}
