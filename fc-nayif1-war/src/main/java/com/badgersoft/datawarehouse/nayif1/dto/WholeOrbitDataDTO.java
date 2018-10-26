package com.badgersoft.datawarehouse.nayif1.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidjohnson on 05/11/2016.
 */
public class WholeOrbitDataDTO implements Serializable {

    private List<WodInstantDTO> wodMinutes = new ArrayList<WodInstantDTO>();

    public WholeOrbitDataDTO() {}

    public WholeOrbitDataDTO(List<WodInstantDTO> wodMinutes) {
        this.wodMinutes = wodMinutes;
    }

    public List<WodInstantDTO> getWodMinutes() {
        return wodMinutes;
    }
}
