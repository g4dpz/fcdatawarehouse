package com.badgersoft.datawarehouse.eseo.dao;

/**
 * Created by davidjohnson on 25/09/2016.
 */

import com.badgersoft.datawarehouse.eseo.domain.MinMaxEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MinMaxDao extends CrudRepository<MinMaxEntity, Long> {

    @Query("SELECT mm FROM MinMaxEntity mm where mm.enabled = 1 order by mm.channel asc")
    List<MinMaxEntity> getAllEnabled();

    @Query("SELECT mm FROM MinMaxEntity mm where mm.channel = ?1 and mm.enabled = 1 order by mm.channel asc")
    List<MinMaxEntity> getChannelEnabled(Long channel);


}