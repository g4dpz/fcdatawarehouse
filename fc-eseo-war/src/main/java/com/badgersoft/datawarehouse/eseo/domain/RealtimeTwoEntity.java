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
@Table(name = "realtime_two", catalog = "eseo")
public class RealtimeTwoEntity extends BaseRealtimeEntity implements RealTime, Serializable {

    private double c32;
    private double c33;
    private double c34;
    private long c35;
    private long c36;
    private long c37;
    private long c38;
    private long c39;
    private long c40;
    private long c41;


    public RealtimeTwoEntity() {}

    public double getC32() {
        return c32;
    }

    public void setC32(double c32) {
        this.c32 = c32;
    }

    public double getC33() {
        return c33;
    }

    public void setC33(double c33) {
        this.c33 = c33;
    }

    public double getC34() {
        return c34;
    }

    public void setC34(double c34) {
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

    public long getC38() {
        return c38;
    }

    public void setC38(long c38) {
        this.c38 = c38;
    }

    public long getC39() {
        return c39;
    }

    public void setC39(long c39) {
        this.c39 = c39;
    }

    public long getC40() {
        return c40;
    }

    public void setC40(long c40) {
        this.c40 = c40;
    }

    public long getC41() {
        return c41;
    }

    public void setC41(long c41) {
        this.c41 = c41;
    }

    @Transient
    private int stringPos = 0;

    @Transient
    public void readBinary(String binaryString) {
        super.readBinary(binaryString);

        // ESEO DATA
        c32 = getBitsAsDouble(32, binaryString);
        c33 = getBitsAsDouble(32, binaryString);
        c34 = getBitsAsDouble(32, binaryString);
        c35 = getBitsAsULong(16, binaryString);
        c36 = getBitsAsULong(8, binaryString);
        c37 = getBitsAsULong(16, binaryString);
        c38 = getBitsAsULong(16, binaryString);
        c39 = getBitsAsSLong(16, binaryString);
        c40 = getBitsAsSLong(12, binaryString);
        c41 = getBitsAsSLong(12, binaryString);
    }


}
