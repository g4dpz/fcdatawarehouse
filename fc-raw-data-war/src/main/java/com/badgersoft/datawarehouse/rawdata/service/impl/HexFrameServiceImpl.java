package com.badgersoft.datawarehouse.rawdata.service.impl;

import com.badgersoft.datawarehouse.common.dto.HexFrameDTO;
import com.badgersoft.datawarehouse.common.utils.Cache;
import com.badgersoft.datawarehouse.common.utils.Clock;
import com.badgersoft.datawarehouse.common.utils.UTCClock;
import com.badgersoft.datawarehouse.rawdata.config.EnvConfig;
import com.badgersoft.datawarehouse.rawdata.dao.*;
import com.badgersoft.datawarehouse.rawdata.domain.*;
import com.badgersoft.datawarehouse.rawdata.messaging.JmsMessageSender;
import com.badgersoft.datawarehouse.rawdata.service.HexFrameService;
import com.badgersoft.datawarehouse.rawdata.service.UserHexString;
import com.badgersoft.datawarehouse.rawdata.utils.ServiceUtility;
import com.badgersoft.satpredict.client.SatPredictClient;
import com.badgersoft.satpredict.client.dto.SatPosDTO;
import com.badgersoft.satpredict.client.impl.SatPredictClientImpl;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

import static com.badgersoft.datawarehouse.rawdata.utils.ServiceUtility.convertHexBytePairToBinary;

@Service
public class HexFrameServiceImpl implements HexFrameService {



    private static final Logger LOG = LoggerFactory.getLogger(HexFrameServiceImpl.class);

    private static final Cache<String, String> USER_AUTH_KEYS = new Cache<String, String>(
            new UTCClock(), 50, 10);

    private static final String HAD_INCORRECT_DIGEST = "] had incorrect digest";
    private static final String USER_WITH_SITE_ID = "User with site id [";
    private static final String NOT_FOUND = "] not found";
    private static final long TWO_DAYS_SEQ_COUNT = 1440;

    public HexFrameServiceImpl() {}

    @Autowired
    EnvConfig envConfig;

    @Autowired
    private HexFrameDao hexFrameDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private Clock clock;

    @Autowired
    private SatelliteStatusDao satelliteStatusDao;

    @Autowired
    private UserRankingDao userRankingDao;

    @Autowired
    JmsMessageSender jmsMessageSender;

    @Autowired
    private PayloadDao payloadDao;

    private SatPredictClient satPredictClient = null;

    public HexFrameServiceImpl(HexFrameDao hexFrameDao, UserDao userDao, Clock clock,
                               SatelliteStatusDao satelliteStatusDao, UserRankingDao userRankingDao,
                               EnvConfig envConfig, JmsMessageSender jmsMessageSender,
                               PayloadDao payloadDao) {
        this.hexFrameDao = hexFrameDao;
        this.userDao = userDao;
        this.clock = clock;
        this.satelliteStatusDao = satelliteStatusDao;
        this.userRankingDao = userRankingDao;
        this.envConfig = envConfig;
        this.jmsMessageSender = jmsMessageSender;
        this.payloadDao = payloadDao;
    }

    @Override
    public String ping() {
        return "Hello";
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

                LOG.debug("Using authKey: " + authKey);

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

                    LOG.info(calculatedDigest + " " + hexString);

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

        final Map<Long, SatelliteStatus> satelliteStatusMap = new HashMap<Long, SatelliteStatus>();

        final List<SatelliteStatus> satelliteStatuses = satelliteStatusDao.findAll();

        for(SatelliteStatus satelliteStatus : satelliteStatuses) {
            satelliteStatusMap.put(satelliteStatus.getSatelliteId(), satelliteStatus);
        }

        if (satelliteStatusMap.size() == 0) {
            LOG.error("--- NO Satellite Statuses Found ---");
        }
        else {
            for(Long satelliteId : satelliteStatusMap.keySet()) {
                LOG.debug("Found satellite status for sateliteId: " + satelliteId);
            }
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

        LOG.info(String.format("Processing %d %d %d", satelliteId, sequenceNumber, frameType));

        if (!satelliteStatusMap.containsKey(satelliteId)) {
            final String noSatelliteStatusFound = String.format("No satelliteStatus found for satellite %d", satelliteId);
            LOG.error(noSatelliteStatusFound);
            return new ResponseEntity<String>(noSatelliteStatusFound, HttpStatus.BAD_REQUEST);
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

        if (hexFrameEntities.isEmpty()) {

            String payloadText = hexString.substring(112);
            String preamble = hexString.substring(0, 112);

            Payload payload = payloadDao.findByHexText(payloadText);

            if (payload == null) {
                payload = new Payload();
                payload.setHexText(payloadText);
                payload.setCreatedDate(new Date(System.currentTimeMillis()));
                payload = payloadDao.save(payload);
            }

            LOG.info(String.format("Saving %d %d %d for %s", satelliteId, sequenceNumber, frameType, user.getSiteId()));
            HexFrame hexFrameEntity = new HexFrame(satelliteId, frameType, sequenceNumber, preamble, createdDate,
                    true, new Timestamp(createdDate.getTime()));
            hexFrameEntity.setOutOfOrder(isOutOfOrder(hexFrameEntity));
            hexFrameEntity.addUser(user);
            hexFrameEntity.setFitterProcessed(false);
            hexFrameEntity.setRealtimeProcessed(false);
            hexFrameEntity.setHighPrecisionProcessed(false);
            hexFrameEntity.setPayload(payload);
            incrementUploadRanking(satelliteId, user.getSiteId(), createdDate);

            addSatellitePosition(hexFrameEntity, satelliteStatusMap.get(satelliteId).getCatalogueNumber());

            hexFrameDao.save(hexFrameEntity);

            if (satelliteId == 0 && sequenceNumber == 0 && firstByte == 0) {
                LOG.warn("--- RESET ---");
                return new ResponseEntity<String>("Satellite RESET", HttpStatus.BAD_REQUEST);
            }

            String queueName = "satellite_" + satelliteId + "_frame_available";

            ActiveMQQueue queue = new ActiveMQQueue(queueName);
            jmsMessageSender.send(queue, String.format("rt,%d,%d,%d",
                    hexFrameEntity.getSatelliteId(),
                    hexFrameEntity.getSequenceNumber(),
                    hexFrameEntity.getFrameType()));
        }
        else {
            HexFrame hexFrameEntity = hexFrameEntities.get(0);
            Iterator<User> userIterator = hexFrameEntity.getUsers().iterator();

            boolean userFound = false;

            while(userIterator.hasNext()) {
                User userEntity = userIterator.next();
                if (user.getSiteId().equals(userEntity.getSiteId())) {
                    userFound = true;
                    break;
                }
            }

            if (userFound) {
                LOG.info(String.format("User %s has already saved %d %d %d", user.getSiteId(), satelliteId, sequenceNumber, frameType));
                return new ResponseEntity<String>("Already Reported", HttpStatus.OK);
            }
            else {
                String siteId = user.getSiteId();
                LOG.info(String.format("Adding user %s to %d %d %d", siteId, satelliteId, sequenceNumber, frameType));
                hexFrameEntity.addUser(user);
                incrementUploadRanking(satelliteId, user.getSiteId(), createdDate);
                hexFrameEntity = hexFrameDao.save(hexFrameEntity);
            }
        }

        return ResponseEntity.ok().build();

    }

    private void incrementUploadRanking(long satelliteId, String siteId, Date createdDate) {
        final Timestamp latestUploadDate = new Timestamp(createdDate.getTime());

        final List<UserRanking> userRankings = userRankingDao
                .findBySatelliteIdAndSiteId(satelliteId, siteId);

        UserRanking userRanking;

        if (userRankings.isEmpty()) {

            userRanking = new UserRanking((long)satelliteId, siteId, 1L,
                    latestUploadDate, latestUploadDate);
        }
        else {
            userRanking = userRankings.get(0);
            userRanking.setLatestUploadDate(latestUploadDate);
            Long number = userRanking.getNumber();
            number++;
            userRanking.setNumber(number);
        }

        userRankingDao.save(userRanking);
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

    private Boolean isOutOfOrder(HexFrame hexFrameEntity) {

        boolean outOfOrder = false;

        final List<HexFrame> existingFrames = hexFrameDao
                .findBySatelliteIdAndSequenceNumber(hexFrameEntity.getSatelliteId(),
                        hexFrameEntity.getSequenceNumber());

        if (!existingFrames.isEmpty()) {
            for (final HexFrame existingFrame : existingFrames) {
                if (existingFrame.getCreatedDate().before(hexFrameEntity.getCreatedDate())
                        && existingFrame.getFrameType() > hexFrameEntity.getFrameType()) {
                    outOfOrder = true;
                    break;
                }
            }
        }

        return outOfOrder;
    }

    private void addSatellitePosition(HexFrame hexFrameEntity, long catalogueNumber) {

        if (satPredictClient == null) {
            satPredictClient = new SatPredictClientImpl(envConfig.satpredictURL());
        }

        SatPosDTO satpos = satPredictClient.getPosition(catalogueNumber, 0.0,0.0,0.0);

        if (satpos != null) {
            hexFrameEntity.setLatitude(satpos.getLatitude());
            hexFrameEntity.setLongitude(satpos.getLongitude());
        }

        return;
    }

    @Override
    @JmsListener(destination = "frame_processed")
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void handleMessage(final String message) {
        LOG.info("Message received: " + message);
    }

    public HexFrameDTO getFrame(long satelliteId, long sequenceNumber, long frameType) {

        final List<HexFrame> hexFrameList = hexFrameDao.findBySatelliteIdAndSequenceNumberAndFrameType(satelliteId, sequenceNumber, frameType);

        if (hexFrameList.isEmpty()) {
            LOG.warn(String.format("No frames found for %d %d %d", satelliteId, sequenceNumber, frameType));
            return null;
        }

        HexFrame hfe = hexFrameList.get(0);
        HexFrameDTO hexFrameDTO = new HexFrameDTO();
        hexFrameDTO.setSatelliteId(hfe.getSatelliteId());
        hexFrameDTO.setSequenceNumber(hfe.getSequenceNumber());
        hexFrameDTO.setFrameType(hfe.getFrameType());
        hexFrameDTO.setHexString(hfe.getHexString());
        hexFrameDTO.setCreatedDate(hfe.getCreatedDate());
        hexFrameDTO.setLatitude(hfe.getLatitude());
        hexFrameDTO.setLongitude(hfe.getLongitude());
        hexFrameDTO.setSatelliteTime(hfe.getSatelliteTime());

        Set<User> userEntities = hfe.getUsers();

        List<String> contributors = new ArrayList(userEntities.size());

        for (User user : userEntities) {
            contributors.add(user.getSiteId());
        }

        Collections.sort(contributors);

        hexFrameDTO.setContributors(contributors);

        return hexFrameDTO;

    }
}