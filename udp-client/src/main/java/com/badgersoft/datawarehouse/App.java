package com.badgersoft.datawarehouse;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {

    private static Map<String, String> satMap = new HashMap<>();

    static {
        satMap.put("AO-73", "2");
        satMap.put("JO-97", "19");
        satMap.put("EO-88", "11");
    }


    public static void main(String[] args) {
        int port = args.length == 0 ? 9932 : Integer.parseInt(args[0]);
        new App().run(port);
    }

    public void run(int port) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(port);
            byte[] receiveData = new byte[1024];

            System.out.printf("Listening on udp:%s:%d%n",
                    InetAddress.getLocalHost().getHostAddress(), port);
            DatagramPacket receivePacket = new DatagramPacket(receiveData,
                    receiveData.length);

            long count = 0;
            String satName = "";

            while (true) {
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData(), 0,
                        receivePacket.getLength());
                if (sentence.contains("AzEl Rotor Report") && count++ % 5 == 0) {
                    String testSatName = getSatId(sentence);
                    if (!satName.equals(testSatName)) {
                        String satId = satMap.get(testSatName);
                        // get the latest hexFrame
                        System.out.println("Getting last frame for satellite id: " + satId);
                        satName = testSatName;
                    }
                    System.out.println("RECEIVED: " + sentence);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        // should close serverSocket in finally block
    }

    private String getSatId(String sentence) {
        int start = sentence.indexOf("SatName:");
        start += 8;
        return sentence.substring(start, sentence.length() - 1);
    }
}
