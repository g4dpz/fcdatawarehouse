package com.badgersoft.datawarehouse.eseo.dao;

import com.badgersoft.datawarehouse.eseo.domain.PayloadTwo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by davidjohnson on 18/09/2016.
 */
public interface PayloadTwoDao extends PagingAndSortingRepository<PayloadTwo, Long> {

    @Query
    PayloadTwo findBySequenceNumberAndFrameType(Long sequenceNumber, Long frameType);
}
