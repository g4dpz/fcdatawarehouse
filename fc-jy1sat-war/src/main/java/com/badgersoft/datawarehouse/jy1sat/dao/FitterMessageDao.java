package com.badgersoft.datawarehouse.jy1sat.dao;

/**
 * Created by davidjohnson on 25/09/2016.
 */

import com.badgersoft.datawarehouse.jy1sat.domain.FitterMessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface FitterMessageDao extends CrudRepository<FitterMessageEntity, Long> {

    @Query
    List<FitterMessageEntity> findByMessageTextAndDebug(String messageText, Boolean debug);

    @Query("select fm from FitterMessageEntity fm where lastReceived < ?1")
    List<FitterMessageEntity> getNoneDebugReceivedBefore(Date time);

    @Query("select fm from FitterMessageEntity fm where lastReceived > ?1 and fm.debug = 0 order by fm.createdDate asc")
    List<FitterMessageEntity> getNoneDebugReceivedAfter(Timestamp time);

    @Query("SELECT fm FROM FitterMessageEntity fm where fm.debug = 1 order by fm.id desc")
    List<FitterMessageEntity> getLatestDebug(Pageable pageable);


}