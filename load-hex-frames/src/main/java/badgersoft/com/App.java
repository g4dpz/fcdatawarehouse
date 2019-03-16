package badgersoft.com;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        
        long epoch = System.currentTimeMillis();

        FileReader fileReader = new FileReader(args[0]);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        List<String> lines = new ArrayList<String>();

        try {
            for (; ; ) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                String newLine = "";
                int i = 0;
                for (; i < line.length() - 2; i += 2) {
                    newLine += String.format("%s ", line.substring(i, i + 2));
                }
                newLine += String.format("%s ", line.substring(i, i + 2));
                lines.add(newLine);
            }
        }
        catch (Exception e) {

        }

        Thread t1 = new Thread(new GroundStation("g4dpz","la3nro15ceoqesi8s441flmu6n", epoch, lines));

        t1.start();

    }

}
