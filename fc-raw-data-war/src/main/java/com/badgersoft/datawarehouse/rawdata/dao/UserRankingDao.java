package com.badgersoft.datawarehouse.rawdata.dao;

import com.badgersoft.datawarehouse.rawdata.domain.UserRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRankingDao extends JpaRepository<UserRanking, Long> {

    @Query
    List<UserRanking> findBySatelliteIdAndSiteId(Long satelliteId, String siteId);
}