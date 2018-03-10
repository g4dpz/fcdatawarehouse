package com.badgersoft.datawarehouse.rawdata.dao;

import com.badgersoft.datawarehouse.rawdata.domain.SatelliteStatus;
import com.badgersoft.datawarehouse.rawdata.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SatelliteStatusDao extends JpaRepository<SatelliteStatus, Long> {

    @Query
    User findBySatelliteId(String siteId);
}