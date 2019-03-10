package com.badgersoft.datawarehouse.funcube.processor;

import java.util.Date;
import java.util.List;

/**
 * Created by davidjohnson on 23/08/2016.
 */
public interface WodProcessor {
    void process(Long sequenceNumber, Date satelliteTime, List<String> frames);
}
