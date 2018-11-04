package com.badgersoft.datawarehouse.funcube.dao;

import com.badgersoft.datawarehouse.funcube.domain.WholeOrbitDataEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Created by davidjohnson on 18/09/2016.
 */
public interface WholeOrbitDataDAO extends PagingAndSortingRepository<WholeOrbitDataEntity, Long> {

    @Query
    Optional<WholeOrbitDataEntity> findById(Long id);

    @Query("select fm from WholeOrbitDataEntity fm where satellite_time >= ?1")
    List<WholeOrbitDataEntity> findAfterSatTime(Timestamp from);
}
