package com.badgersoft.datawarehouse.migrate;

import com.badgersoft.datawarehouse.migrate.dao.HexFrameDao;
import com.badgersoft.datawarehouse.migrate.domain.HexFrameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class App implements CommandLineRunner {

    private static final int HEX_0X0F = 0x0F;

    @Autowired
    private HexFrameDao hexFrameDao;


    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class)
                .web(WebApplicationType.NONE) // .REACTIVE, .SERVLET
                .run(args);
    }

    @Override
    public void run(String... arg0) {
        List<HexFrameEntity> hexFrameEntities = new ArrayList<>();
        Pageable pageable;
        long count = 2421039;
        do {
            hexFrameEntities = getFrames(2, count, count+99);
            count+=100;
            for (int i = 0; i < hexFrameEntities.size(); i++) {
                sendFrame(hexFrameEntities.get(i).getHexString());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(String.format("Count: %d, size: %d", count, hexFrameEntities.size()));
        } while (count < 3e6) ;
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    List<HexFrameEntity> getFrames(long satelliteId, long start, long end) {
        return hexFrameDao.findBySatelliteIdAndIdBetweenQuery(satelliteId, start, end);
    }

    private void sendFrame(String hexString) {
        try {
            String digest = calculateDigest(hexString, "la3nro15ceoqesi8s441flmu6n", null);
            String data = "data=" + hexString;

            //String baseUrl = "http://data2.amsat-uk.org/api/data/hex/" + id + "/?digest=" + digest;
            String baseUrl = "http://localhost:8080/api/data/hex/" + "g4dpz" + "/?digest=" + digest;

            URL obj = new URL(baseUrl);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/text");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.getOutputStream().write(data.getBytes("UTF-8"));

            int responseCode = conn.getResponseCode();
            conn.disconnect();

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