package com.badgersoft.datawarehouse.jy1sat.dto;

import java.io.Serializable;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class AntsDTO implements Serializable {

    private String antTemp0;
    private String antTemp1;
    private String antDepl0;
    private String antDepl1;
    private String antDepl2;
    private String antDepl3;

    public AntsDTO() {}

    public AntsDTO(String antTemp0, String antTemp1, String antDepl0, String antDepl1, String antDepl2, String antDepl3) {
        this.antTemp0 = antTemp0;
        this.antTemp1 = antTemp1;
        this.antDepl0 = antDepl0;
        this.antDepl1 = antDepl1;
        this.antDepl2 = antDepl2;
        this.antDepl3 = antDepl3;
    }

    public String getAntTemp0() {
        return antTemp0;
    }

    public String getAntTemp1() {
        return antTemp1;
    }

    public String getAntDepl0() {
        return antDepl0;
    }

    public String getAntDepl1() {
        return antDepl1;
    }

    public String getAntDepl2() {
        return antDepl2;
    }

    public String getAntDepl3() {
        return antDepl3;
    }
}
