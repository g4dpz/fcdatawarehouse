package com.badgersoft.datawarehouse.jy1sat;

import com.badgersoft.datawarehouse.jy1sat.messaging.Receiver;
import com.badgersoft.datawarehouse.jy1sat.messaging.Sender;

public class MessagingTest {

    private static Sender sender = new Sender();
    private static Receiver receiver = new Receiver();

    public static void main(String... args) {
        sender.send("foo", "bar");
    }

}
