package com.badgersoft.datawarehouse.funcube.service;

import com.badgersoft.datawarehouse.funcube.dto.WodDTO;

public class WODServiceImpl implements WODService {

    @Override
    public WodDTO getLatestWOD() {
        return new WodDTO();
    }
}
