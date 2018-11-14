package com.badgersoft.datawarehouse.funcube.processor;

import com.badgersoft.datawarehouse.common.dto.HexFrameDTO;

/**
 * Created by davidjohnson on 23/08/2016.
 */
public interface WodProcessor {
    void process(Long sequenceNumber, HexFrameDTO[] frames);
}