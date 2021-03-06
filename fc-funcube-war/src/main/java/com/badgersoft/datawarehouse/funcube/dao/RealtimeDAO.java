package com.badgersoft.datawarehouse.funcube.dao;

import com.badgersoft.datawarehouse.funcube.domain.RealtimeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by davidjohnson on 18/09/2016.
 */
public interface RealtimeDAO extends PagingAndSortingRepository<RealtimeEntity, Long> {

    @Query
    RealtimeEntity findBySequenceNumberAndFrameType(Long sequenceNumber, Long frameType);

    @Query
    RealtimeEntity findFirst1ByOrderByIdDesc();
}
