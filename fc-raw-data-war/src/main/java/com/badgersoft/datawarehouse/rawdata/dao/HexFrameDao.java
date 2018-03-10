package com.badgersoft.datawarehouse.rawdata.dao;

import com.badgersoft.datawarehouse.rawdata.domain.HexFrame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HexFrameDao extends JpaRepository<HexFrame, Long> {

    @Query
    List<HexFrame> findBySatelliteIdAndSequenceNumberAndFrameType(long satelliteId, long sequenceNumber, long frameType);

    @Query("SELECT max(sequenceNumber) from HexFrame where satelliteId = ?1")
    Long getMaxSequenceNumber(long satelliteId);

    @Query
    List<HexFrame> findBySatelliteIdAndSequenceNumber(Long satelliteId, Long sequenceNumber);
}