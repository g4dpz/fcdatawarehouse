package com.badgersoft.datawarehouse.funcube.service;

import com.badgersoft.datawarehouse.funcube.messaging.Receiver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class BackfillRealtimeServiceImpl implements BackfillRealtimeService {

    @Override
    public void backfill() throws Exception {

        // Get the file
        File f = new File("/tmp/missing-fc1.txt");

        // Check if the specified file
        // Exists or not
        if (f.exists()) {
            Receiver receiver = new Receiver();
            System.out.println("/tmp/missing-fc1.txt Exists");
            FileReader fileReader = new FileReader(f);
            BufferedReader br = new BufferedReader(fileReader);
            boolean line;
            while (line = br.readLine() != null) {
                receiver.receive("rt,2," + line);
            }
            f.delete();
        }
        else
            System.out.println("/tmp/missing-fc1.txt Does not Exist");

    }
}
