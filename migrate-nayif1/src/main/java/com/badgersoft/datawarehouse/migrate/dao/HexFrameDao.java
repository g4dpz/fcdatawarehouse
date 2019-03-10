package com.badgersoft.datawarehouse.migrate.dao;

import com.badgersoft.datawarehouse.migrate.domain.HexFrame;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HexFrameDao extends CrudRepository<HexFrame, Long> {

    @Query("select hfe from HexFrame hfe where satelliteId = ?1 and id between ?2 and ?3 and valid = 1")
    List<HexFrame> findBySatelliteIdAndIdBetweenQuery(long satelliteId, long startId, long endId);

}
