package badgersoft.com;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by davidjohnson on 02/10/2016.
 */
public class GroundStation implements Runnable {

    private final String id;
    private final long epoch;
    private static final int HEX_0X0F = 0x0F;
    private final String authCode;
    private final AtomicInteger count = new AtomicInteger(0);
    private final List<String> lines;
    private final Random rand = new Random();

    public GroundStation(String id, String authCode, long epoch, List<String> lines) {
        this.id = id;
        this.authCode = authCode;
        this.epoch = epoch;
        this.lines = lines;
    }

    public void run() {

       for(int i = 0; i < lines.size(); i++) {
           try {
               long now = System.currentTimeMillis();
               long satelliteTime = now - epoch;
               double sequences = satelliteTime / 120000.0;
               long sequence = (long) Math.floor(sequences);
               long frameType = (long) ((sequences - sequence) * 24);
               System.out.println(String.format("GS %s sending %d %d", id, sequence, frameType));
               String hexFrame = lines.get(count.getAndIncrement());
               String digest = calculateDigest(hexFrame, authCode, null);
               String data = "data=" + hexFrame;

               //String baseUrl = "http://data2.amsat-uk.org/api/data/hex/" + id + "/?digest=" + digest;
               String baseUrl = "http://localhost:8080/api/data/hex/" + id + "/?digest=" + digest;
//               String baseUrl = "http://data.amsat-uk.org/api/data/hex/" + id + "/?digest=" + digest;

               URL obj = new URL(baseUrl);
               HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
               conn.setRequestMethod("POST");
               conn.setRequestProperty("Content-Type", "application/text");
               conn.setDoInput(true);
               conn.setDoOutput(true);
               conn.getOutputStream().write(data.getBytes("UTF-8"));

               int responseCode = conn.getResponseCode();
               conn.disconnect();
               System.out.println("Response to call from GS: " + id + " was: " + responseCode);

                int  n = 5000;
               Thread.sleep(n);
           } catch (InterruptedException e) {
               e.printStackTrace();
           } catch (NoSuchAlgorithmException e) {
               e.printStackTrace();
           } catch (UnsupportedEncodingException e) {
               e.printStackTrace();
           } catch (ProtocolException e) {
               e.printStackTrace();
           } catch (MalformedURLException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

    }

    private static String calculateDigest(final String hexString,
                                          final String authCode, final Integer utf)
            throws NoSuchAlgorithmException {

        String digest = null;

        final MessageDigest md5 = MessageDigest.getInstance("MD5");

        if (utf == null) {

            md5.update(hexString.getBytes());
            md5.update(":".getBytes());
            digest = convertToHex(md5.digest(authCode.getBytes()));
        }
        else if (utf.intValue() == 8) {
            md5.update(hexString.getBytes(Charset.forName("UTF8")));
            md5.update(":".getBytes(Charset.forName("UTF8")));
            digest = convertToHex(md5.digest(authCode.getBytes(Charset
                    .forName("UTF8"))));
        }
        else {
            md5.update(hexString.getBytes(Charset.forName("UTF16")));
            md5.update(":".getBytes(Charset.forName("UTF16")));
            digest = convertToHex(md5.digest(authCode.getBytes(Charset
                    .forName("UTF16"))));
        }

        return digest;
    }

    private static String convertToHex(final byte[] data) {
        final StringBuffer buf = new StringBuffer();
        for (final byte element : data) {
            int halfbyte = (element >>> 4) & HEX_0X0F;
            int twoHalfs = 0;
            do {
                if (0 <= halfbyte && halfbyte <= 9) {
                    buf.append((char)('0' + halfbyte));
                }
                else {
                    buf.append((char)('a' + (halfbyte - 10)));
                }
                halfbyte = element & HEX_0X0F;
            }
            while (twoHalfs++ < 1);
        }
        return buf.toString();
    }
}
