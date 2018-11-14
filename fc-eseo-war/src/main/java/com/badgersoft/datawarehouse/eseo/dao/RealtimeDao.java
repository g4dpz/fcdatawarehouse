package com.badgersoft.datawarehouse.eseo.dao;

import com.badgersoft.datawarehouse.eseo.domain.RealtimeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by davidjohnson on 17/10/2016.
 */
public interface RealtimeDao extends PagingAndSortingRepository<RealtimeEntity, Long> {

    @Query
    RealtimeEntity findBySequenceNumberAndFrameType(Long sequenceNumber, Long frameType);
}
