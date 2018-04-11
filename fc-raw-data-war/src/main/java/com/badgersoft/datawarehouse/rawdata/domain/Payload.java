package com.badgersoft.datawarehouse.rawdata.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "payload")
public class Payload implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hex_text")
    private String hexText;

    @Column(name = "created_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdDate;

    public Payload() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHexText() {
        return hexText;
    }

    public void setHexText(String hexText) {
        this.hexText = hexText;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "id=" + id +
                ", hexText='" + hexText + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
