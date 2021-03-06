package com.badgersoft.datawarehouse.eseo.dao;

/**
 * Created by davidjohnson on 25/09/2016.
 */

import com.badgersoft.datawarehouse.eseo.domain.FitterMessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface FitterMessageDao extends CrudRepository<FitterMessageEntity, Long> {

    @Query
    List<FitterMessageEntity> findByMessageTextAndDebug(String messageText, Boolean debug);

    @Query("select fm from FitterMessageEntity fm where lastReceived < ?2")
    List<FitterMessageEntity> getNoneDebugReceivedBefore(Date time);

    @Query("select fm from FitterMessageEntity fm where lastReceived > ?2 and fm.debug = 0 and fm.display = 1 order by fm.createdDate asc")
    List<FitterMessageEntity> getNoneDebugReceivedAfter(Timestamp time);

    @Query("SELECT fm FROM FitterMessageEntity fm where fm.debug = 1 order by fm.id desc")
    List<FitterMessageEntity> getLatestDebug(Pageable pageable);

}