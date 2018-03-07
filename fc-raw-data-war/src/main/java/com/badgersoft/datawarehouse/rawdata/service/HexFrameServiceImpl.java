package com.badgersoft.datawarehouse.rawdata.service;

import com.badgersoft.datawarehouse.common.services.AbstractHexFrameService;
import com.badgersoft.datawarehouse.common.services.HexFrameService;
import com.badgersoft.datawarehouse.common.utils.Cache;
import com.badgersoft.datawarehouse.common.utils.Clock;
import com.badgersoft.datawarehouse.common.utils.UTCClock;
import com.badgersoft.datawarehouse.rawdata.dao.HexFrameDao;
import com.badgersoft.datawarehouse.rawdata.dao.UserDao;
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

import java.util.Date;

@Service
@Component(value = "rawdataHFS")
public class HexFrameServiceImpl extends AbstractHexFrameService implements HexFrameService {

    private static final Logger LOG = LoggerFactory.getLogger(HexFrameServiceImpl.class);

    private final HexFrameDao hexFrameDao;
    private final UserDao userDao;
    private final Clock clock;

    private static final Cache<String, String> USER_AUTH_KEYS = new Cache<String, String>(
            new UTCClock(), 50, 10);

    private static final String HAD_INCORRECT_DIGEST = "] had incorrect digest";
    private static final String USER_WITH_SITE_ID = "User with site id [";
    private static final String NOT_FOUND = "] not found";

    @Autowired
    public HexFrameServiceImpl(HexFrameDao hexFrameDao, UserDao userDao, Clock clock) {
        this.hexFrameDao = hexFrameDao;
        this.userDao = userDao;
        this.clock = clock;
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
        return ResponseEntity.ok().build();
    }

}
