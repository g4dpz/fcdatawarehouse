package com.badgersoft.datawarehouse.rawdata.service;

import com.badgersoft.datawarehouse.common.dto.ValMinMaxDTO;

import java.util.List;

public interface FitterMessageService {
    List<ValMinMaxDTO> getMessages(String url);
}
