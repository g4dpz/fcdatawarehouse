package com.badgersoft.datawarehouse.rawdata.dao;

import com.badgersoft.datawarehouse.rawdata.domain.Epoch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpochDao extends JpaRepository<Epoch, Long> {

}