package com.badgersoft.datawarehouse.nayif1.dao;

import com.badgersoft.datawarehouse.nayif1.domain.HighResolutionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by davidjohnson on 18/09/2016.
 */
public interface HighResolutionDAO extends PagingAndSortingRepository<HighResolutionEntity, Date> {

    @Query
    List<HighResolutionEntity> findLast60ByOrderBySatelliteTimeAsc();

    @Query("select fm from HighResolutionEntity fm where satellite_time >= ?1")
    List<HighResolutionEntity> findAfterSatTime(Timestamp from);
}
