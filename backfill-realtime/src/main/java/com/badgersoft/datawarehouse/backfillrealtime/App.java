package com.badgersoft.datawarehouse.backfillrealtime;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {

        File file = new File(args[1]);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue destination = session.createQueue("satellite_" + args[0] + "_frame_available");
        MessageProducer producer = session.createProducer(destination);

        try {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                TextMessage message = session.createTextMessage();
                message.setText("rt," + args[0] + "," + line);
                producer.send(message);
                Thread.sleep(3000);
            }
        } finally {
            producer.close();
        }

        connection.close();
    }
}
