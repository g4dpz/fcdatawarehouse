package com.badgersoft.datawarehouse.eseo.dao;

import com.badgersoft.datawarehouse.eseo.domain.RealtimeTwoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by davidjohnson on 18/09/2016.
 */
public interface RealtimeTwoDao extends PagingAndSortingRepository<RealtimeTwoEntity, Long> {

    @Query
    RealtimeTwoEntity findBySequenceNumberAndFrameType(Long sequenceNumber, Long frameType);
}
