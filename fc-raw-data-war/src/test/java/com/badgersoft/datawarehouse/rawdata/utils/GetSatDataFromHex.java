package com.badgersoft.datawarehouse.rawdata.utils;

public class GetSatDataFromHex {

    public static void main(String... args) {
        //String binary = convertHexBytePairToBinary("570C");
        String binary = "0000110001010111";
        int firstByte = Integer.parseInt(binary.substring(0, 8), 2);
        int secondByte = Integer.parseInt(binary.substring(8, 16), 2);

        long satelliteId = ((firstByte & 192) >> 6);

        if (satelliteId == 3) {
            satelliteId += ((secondByte & 252));
        }

        long frameType = (firstByte & 63);

        System.out.println(satelliteId);
    }
}
