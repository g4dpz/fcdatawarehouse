package com.badgersoft.datawarehouse.eseo.dao;

import com.badgersoft.datawarehouse.eseo.domain.PayloadOneEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by davidjohnson on 18/09/2016.
 */
public interface PayloadOneDao extends PagingAndSortingRepository<PayloadOneEntity, Long> {

    @Query
    PayloadOneEntity findBySequenceNumberAndFrameType(Long sequenceNumber, Long frameType);

}
