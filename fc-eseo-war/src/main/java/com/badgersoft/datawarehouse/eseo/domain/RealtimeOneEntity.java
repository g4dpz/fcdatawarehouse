package com.badgersoft.datawarehouse.eseo.domain;

import com.badgersoft.datawarehouse.eseo.dao.RealTime;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by davidjohnson on 18/09/2016.
 */
@Entity
@Table(name = "realtime_one", catalog = "eseo")
public class RealtimeOneEntity extends BaseRealtimeEntity implements RealTime, Serializable {

    private long c32;
    private long c33;
    private long c34;
    private long c35;
    private long c36;
    private long c37;
    private float c38;
    private float c39;
    private float c40;
    private long c41;
    private long c42;


    public RealtimeOneEntity() {}

    public long getC32() {
        return c32;
    }

    public void setC32(long c32) {
        this.c32 = c32;
    }

    public long getC33() {
        return c33;
    }

    public void setC33(long c33) {
        this.c33 = c33;
    }

    public long getC34() {
        return c34;
    }

    public void setC34(long c34) {
        this.c34 = c34;
    }

    public long getC35() {
        return c35;
    }

    public void setC35(long c35) {
        this.c35 = c35;
    }

    public long getC36() {
        return c36;
    }

    public void setC36(long c36) {
        this.c36 = c36;
    }

    public long getC37() {
        return c37;
    }

    public void setC37(long c37) {
        this.c37 = c37;
    }

    public float getC38() {
        return c38;
    }

    public void setC38(float c38) {
        this.c38 = c38;
    }

    public float getC39() {
        return c39;
    }

    public void setC39(float c39) {
        this.c39 = c39;
    }

    public float getC40() {
        return c40;
    }

    public void setC40(float c40) {
        this.c40 = c40;
    }

    public long getC41() {
        return c41;
    }

    public void setC41(long c41) {
        this.c41 = c41;
    }

    public long getC42() {
        return c42;
    }

    public void setC42(long c42) {
        this.c42 = c42;
    }

    @Transient
    public void readBinary(String binaryString) {
        super.readBinary(binaryString);

        // AMSAT DATA
        c32 = getBitsAsULong(16, binaryString);
        c33 = getBitsAsULong(16, binaryString);
        c34 = getBitsAsULong(16, binaryString);
        c35 = getBitsAsULong(8, binaryString);
        c36 = getBitsAsULong(20, binaryString);
        c37 = getBitsAsULong(8, binaryString);

        c38 = getBitsAsFloat(32, binaryString);
        c39 = getBitsAsFloat(32, binaryString);
        c40 = getBitsAsFloat(32, binaryString);
        c41 = getBitsAsULong(8, binaryString);
        c42 = getBitsAsULong(8, binaryString);
    }


}
