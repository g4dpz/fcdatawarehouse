package com.badgersoft.datawarehouse.eseo.dto;

abstract class BaseDTO {

    protected String formatOneDP(double value) {
        return String.format("%10.1f", value).trim();
    }

}
