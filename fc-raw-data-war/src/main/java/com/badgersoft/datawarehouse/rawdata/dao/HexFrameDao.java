package com.badgersoft.datawarehouse.rawdata.dao;

import com.badgersoft.datawarehouse.rawdata.domain.HexFrame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HexFrameDao extends JpaRepository<HexFrame, Long> {
}