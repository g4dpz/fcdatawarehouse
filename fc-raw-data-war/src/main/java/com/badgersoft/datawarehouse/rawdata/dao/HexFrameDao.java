package com.badgersoft.datawarehouse.rawdata.dao;

import com.badgersoft.datawarehouse.rawdata.domain.HexFrame;
import com.badgersoft.datawarehouse.rawdata.domain.Payload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HexFrameDao extends JpaRepository<HexFrame, Long> {

    @Query
    List<HexFrame> findBySatelliteIdAndSequenceNumberAndFrameType(long satelliteId, long sequenceNumber, long frameType);

    @Query("SELECT max(sequenceNumber) from HexFrame where satelliteId = ?1")
    Long getMaxSequenceNumber(long satelliteId);

    @Query
    List<HexFrame> findBySatelliteIdAndSequenceNumber(Long satelliteId, Long sequenceNumber);

    @Query
    List<HexFrame> findBySatelliteIdAndSequenceNumberAndFrameTypeInOrderByFrameTypeAsc(Long satelliteId, Long sequenceNumber, List<Long> frames);

    @Modifying(clearAutomatically = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Query("update HexFrame hf set hf.payload = ?1 where hf.satelliteId = ?2 and hf.sequenceNumber = ?3 and hf.frameType = ?4")
    int setPayload(Payload payload, long satelliteId, long sequenceNumber, long frameType);
}