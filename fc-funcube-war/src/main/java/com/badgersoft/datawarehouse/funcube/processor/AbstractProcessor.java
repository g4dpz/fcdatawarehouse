package com.badgersoft.datawarehouse.funcube.processor;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by davidjohnson on 25/09/2016.
 */
public class AbstractProcessor {

    protected static String convertHexBytePairToBinary(final String hexString) {
        final StringBuffer sb = new StringBuffer();

        for (int i = 0; i < hexString.length(); i += 2) {
            final String hexByte = hexString.substring(i, i + 2);
            final int hexValue = Integer.parseInt(hexByte, 16);
            sb.append(StringUtils.leftPad(Integer.toBinaryString(hexValue), 8, "0"));
        }
        return sb.toString();
    }
}
