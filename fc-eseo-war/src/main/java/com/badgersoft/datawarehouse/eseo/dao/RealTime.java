package com.badgersoft.datawarehouse.eseo.dao;

import java.util.Date;

/**
 * Created by davidjohnson on 17/10/2016.
 */
public interface RealTime {
    void setSequenceNumber(Long sequenceNumber);

    void setFrameType(Long frameType);

    void setCreatedDate(Date createdDate);

    void setSatelliteTime(Date now);

    void setLatitude(String latitude);

    void setLongitude(String longitude);

    void readBinary(String binaryString);

    Date getSatelliteTime();
}
