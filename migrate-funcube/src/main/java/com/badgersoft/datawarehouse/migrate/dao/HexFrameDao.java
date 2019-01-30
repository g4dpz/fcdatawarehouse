package com.badgersoft.datawarehouse.migrate.dao;

import com.badgersoft.datawarehouse.migrate.domain.HexFrameEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HexFrameDao extends CrudRepository<HexFrameEntity, Long> {

    @Query("select hfe from HexFrameEntity hfe where id between ?1 and ?2 and satelliteId = ?3 and valid = 1")
    List<HexFrameEntity> findByIdBetweenAndSatelliteIdQuery(long startId, long endId, long satelliteId);

}
