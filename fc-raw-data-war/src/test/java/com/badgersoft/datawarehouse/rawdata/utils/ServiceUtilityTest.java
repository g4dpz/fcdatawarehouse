package com.badgersoft.datawarehouse.rawdata.utils;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class ServiceUtilityTest {

    private static String HEX_STRING = "1234567890abcdef";
    private static String AUTH_CODE = "kcjblqskhfpiuhlkdbqlkjdsbliugc1p9u31p9fucpqjb";

    @Test
    public void calculateDefault() throws NoSuchAlgorithmException {
        assertEquals("aa00c2464fe804c31189f7eb2ef3a091", ServiceUtility.calculateDigest(HEX_STRING, AUTH_CODE, null));
    }

    @Test
    public void calculateUTF_8() throws NoSuchAlgorithmException {
        assertEquals("aa00c2464fe804c31189f7eb2ef3a091", ServiceUtility.calculateDigest(HEX_STRING, AUTH_CODE, 8));
    }

    @Test
    public void calculateUTF_16() throws NoSuchAlgorithmException {
        assertEquals("c129c1f2f50186e142ef959a1738b039", ServiceUtility.calculateDigest(HEX_STRING, AUTH_CODE, 16));
    }
}
