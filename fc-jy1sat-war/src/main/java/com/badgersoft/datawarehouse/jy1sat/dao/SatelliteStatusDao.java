package com.badgersoft.datawarehouse.jy1sat.dao;

import com.badgersoft.datawarehouse.jy1sat.domain.SatelliteStatusEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by davidjohnson on 25/09/2016.
 */
public interface SatelliteStatusDao extends CrudRepository<SatelliteStatusEntity, Long> {
}
