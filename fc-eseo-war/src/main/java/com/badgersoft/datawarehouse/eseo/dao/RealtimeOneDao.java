package com.badgersoft.datawarehouse.eseo.dao;

import com.badgersoft.datawarehouse.eseo.domain.RealtimeOneEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by davidjohnson on 18/09/2016.
 */
public interface RealtimeOneDao extends PagingAndSortingRepository<RealtimeOneEntity, Long> {

    @Query
    RealtimeOneEntity findBySequenceNumberAndFrameType(Long sequenceNumber, Long frameType);
}
