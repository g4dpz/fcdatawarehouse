package com.badgersoft.datawarehouse.rawdata.service;

import com.badgersoft.datawarehouse.common.services.AbstractHexFrameService;
import com.badgersoft.datawarehouse.common.services.HexFrameService;
import com.badgersoft.datawarehouse.common.utils.Cache;
import com.badgersoft.datawarehouse.common.utils.Clock;
import com.badgersoft.datawarehouse.common.utils.UTCClock;
import com.badgersoft.datawarehouse.rawdata.dao.EpochDao;
import com.badgersoft.datawarehouse.rawdata.dao.HexFrameDao;
import com.badgersoft.datawarehouse.rawdata.dao.UserDao;
import com.badgersoft.datawarehouse.rawdata.domain.Epoch;
import com.badgersoft.datawarehouse.rawdata.domain.HexFrame;
import com.badgersoft.datawarehouse.rawdata.domain.User;
import com.badgersoft.datawarehouse.rawdata.utils.ServiceUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.badgersoft.datawarehouse.rawdata.utils.ServiceUtility.convertHexBytePairToBinary;

@Service
@Component(value = "rawdataHFS")
public class HexFrameServiceImpl extends AbstractHexFrameService implements HexFrameService {

    private static final Logger LOG = LoggerFactory.getLogger(HexFrameServiceImpl.class);

    private final HexFrameDao hexFrameDao;
    private final UserDao userDao;
    private final EpochDao epochDao;
    private final Clock clock;

    private static final Cache<String, String> USER_AUTH_KEYS = new Cache<String, String>(
            new UTCClock(), 50, 10);

    private static final String HAD_INCORRECT_DIGEST = "] had incorrect digest";
    private static final String USER_WITH_SITE_ID = "User with site id [";
    private static final String NOT_FOUND = "] not found";
    private static final long TWO_DAYS_SEQ_COUNT = 1440;

    @Autowired
    public HexFrameServiceImpl(HexFrameDao hexFrameDao, UserDao userDao, Clock clock, EpochDao epochDao) {
        this.hexFrameDao = hexFrameDao;
        this.userDao = userDao;
        this.clock = clock;
        this.epochDao = epochDao;
    }

    public ResponseEntity processHexFrame(String siteId, String digest, String body) {

        final User user = userDao.findBySiteId(siteId);

        String authKey;

        if (user != null) {
            String hexString;
            if (body.contains("&")) {
                hexString = StringUtils.substringBetween(body, "=", "&");
            } else {
                hexString = body.substring(body.indexOf("=") + 1);
            }

            hexString = hexString.replace("+", " ");

            authKey = USER_AUTH_KEYS.get(siteId);


            try {

                if (authKey == null) {
                    authKey = user.getAuthKey();
                    USER_AUTH_KEYS.put(siteId, authKey);
                }

                final String calculatedDigest = ServiceUtility.calculateDigest(hexString,
                        authKey, null);
                final String calculatedDigestUTF8 = ServiceUtility.calculateDigest(hexString,
                        authKey, Integer.valueOf(8));
                final String calculatedDigestUTF16 = ServiceUtility.calculateDigest(hexString,
                        authKey, Integer.valueOf(16));

                if (null != digest
                        && (digest.equals(calculatedDigest)
                        || digest.equals(calculatedDigestUTF8) || digest
                        .equals(calculatedDigestUTF16))) {

                    hexString = StringUtils.deleteWhitespace(hexString);

                    final Date now = new Date(5000 * (clock.currentDate().getTime() / 5000));

                    return processHexFrame(new UserHexString(user,
                            StringUtils.deleteWhitespace(hexString), now));
                } else {
                    LOG.error(USER_WITH_SITE_ID + siteId + HAD_INCORRECT_DIGEST
                            + ", received: " + digest + ", calculated: "
                            + calculatedDigest);
                    return new ResponseEntity<String>("UNAUTHORIZED",
                            HttpStatus.UNAUTHORIZED);
                }

            } catch (final Exception e) {
                LOG.error(e.getMessage());
                return new ResponseEntity<String>(e.getMessage(),
                        HttpStatus.BAD_REQUEST);
            }

        } else {
            LOG.error("Site id: " + siteId + " not found in database");
            return new ResponseEntity<String>("UNAUTHORIZED",
                    HttpStatus.UNAUTHORIZED);
        }
    }

    private ResponseEntity processHexFrame(UserHexString userHexString) {

        final Map<Long, Epoch> epochMap = new HashMap<Long, Epoch>();

        final Iterator<Epoch> iterator = epochDao.findAll().iterator();

        while (iterator.hasNext()) {
            final Epoch epoch = iterator.next();
            epochMap.put(epoch.getSatelliteId(), epoch);
        }

        final String hexString = userHexString.getHexString();
        final User user = userHexString.getUser();
        final Date createdDate = userHexString.getCreatedDate();


        String binary = convertHexBytePairToBinary(hexString);
        int firstByte = Integer.parseInt(binary.substring(0, 8), 2);
        int secondByte = Integer.parseInt(binary.substring(8, 16), 2);

        long satelliteId = ((firstByte & 192) >> 6);

        if (satelliteId == 3) {
            satelliteId += ((secondByte & 252));
        }

        long frameType = (firstByte & 63);

        final long sequenceNumber = getSequenceNumber(satelliteId, hexString);

        LOG.debug(String.format("Processing %d %d %d", satelliteId, sequenceNumber, frameType));

        if (!epochMap.containsKey(satelliteId)) {
            final String noEpochFound = String.format("No epoch found for satellite %d", satelliteId);
            LOG.error(noEpochFound);
            return new ResponseEntity<String>(noEpochFound, HttpStatus.BAD_REQUEST);
        }

        /* ----------------------------------------
        Satellite no         Satellite ID            Satellite name
        decimal         Decimal   Binary      HEX
        0               0          00000000   00      FUNcube EM
        1               1          00000001   01      Ukube
        2               2          00000010   02      FUNcube-1
        3               3          00000011   03      EXTENDED PROTOCOL START
        4               7          00000111   07      ESEO
        5               11         00001011   0B      NAYIF-1
        6               15         00001111   0F      JY1SAT EM
        7               19         00010011   13      JY1SAT FM
         */

        // bomb out early if the upload is out of bounds
        if (satelliteId != 1) {

            final Long maxSequenceNumber = hexFrameDao
                    .getMaxSequenceNumber(satelliteId);

            if (maxSequenceNumber != null
                    && (maxSequenceNumber - sequenceNumber) > TWO_DAYS_SEQ_COUNT) {
                final String message = String
                        .format("User %s loading sequence number %d is out of bounds for satelliteId %d",
                                user.getSiteId(), sequenceNumber,
                                satelliteId);
                LOG.warn(message);

                return new ResponseEntity<String>("Already Reported", HttpStatus.ALREADY_REPORTED);
            }

        }

        List<HexFrame> hexFrameEntities = hexFrameDao.findBySatelliteIdAndSequenceNumberAndFrameType(satelliteId, sequenceNumber, frameType);

        return ResponseEntity.ok().build();

    }

    private static long getSequenceNumber(long satelliteId, String hexString) {

        final String binaryString = convertHexBytePairToBinary(hexString
                .substring((satelliteId < 3) ? 2 : 4, hexString.length()));

        switch((int) satelliteId) {
            // FUNcube (EM/FM)
            case 0:
            case 2:
                return Long.parseLong(binaryString.substring(392, 392 + 24), 2);
            // UKube1
            case 1:
                return Long.parseLong(binaryString.substring(402, 402 + 24), 2);
            // ESEO
            case 7:
                return Long.parseLong(binaryString.substring(168, 168 + 24), 2);
            //Nayif
            case 11:
                // Jy1Sat_EM
            case 15:
                // Jy1Sat_FM
            case 19:
                return Long.parseLong(binaryString.substring(384, 384 + 24), 2);
            default:
                return -1L;
        }
    }

}
