package com.badgersoft.datawarehouse.jy1sat.processor;


import com.badgersoft.datawarehouse.common.dto.HexFrameDTO;

/**
 * Created by davidjohnson on 23/08/2016.
 */
public interface HighResProcessor {
    void process(Long sequenceNumber, HexFrameDTO[] frames);
}
