package com.badgersoft.datawarehouse.nayif1.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by davidjohnson on 06/11/2016.
 */
public class HighResolutionDTO implements Serializable {

    private List<HighResInstantDTO> highResSeconds;

    public HighResolutionDTO() {}

    public HighResolutionDTO(List<HighResInstantDTO> highResSeconds) {

        this.highResSeconds = highResSeconds;
    }

    public List<HighResInstantDTO> getHighResSeconds() {
        return highResSeconds;
    }

    public void setHighResSeconds(List<HighResInstantDTO> highResSeconds) {
        this.highResSeconds = highResSeconds;
    }
}
