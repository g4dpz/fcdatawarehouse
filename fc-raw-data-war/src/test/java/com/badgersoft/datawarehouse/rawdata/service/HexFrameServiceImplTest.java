package com.badgersoft.datawarehouse.rawdata.service;

import com.badgersoft.datawarehouse.common.services.HexFrameService;
import com.badgersoft.datawarehouse.common.utils.Clock;
import com.badgersoft.datawarehouse.common.utils.UTCClock;
import com.badgersoft.datawarehouse.rawdata.config.AppConfig;
import com.badgersoft.datawarehouse.rawdata.config.EnvConfig;
import com.badgersoft.datawarehouse.rawdata.config.TestJpaConfig;
import com.badgersoft.datawarehouse.rawdata.dao.SatelliteStatusDao;
import com.badgersoft.datawarehouse.rawdata.dao.HexFrameDao;
import com.badgersoft.datawarehouse.rawdata.dao.UserDao;
import com.badgersoft.datawarehouse.rawdata.dao.UserRankingDao;
import com.badgersoft.datawarehouse.rawdata.domain.SatelliteStatus;
import com.badgersoft.datawarehouse.rawdata.domain.HexFrame;
import com.badgersoft.datawarehouse.rawdata.domain.User;
import com.badgersoft.datawarehouse.rawdata.domain.UserRanking;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class, TestJpaConfig.class},
        loader = AnnotationConfigContextLoader.class)

@Transactional
public class HexFrameServiceImplTest {

    private HexFrameService hexFrameService;
    private UserDao mockUserDao;
    private HexFrameDao mockHexFrameDao;
    private Clock mockClock;
    private SatelliteStatusDao mockSatelliteStatusDao;
    private UserRankingDao mockUserRankingDao;
    private EnvConfig mockEnvConfig;

    private static String SITE_ID_1 = "g4dpz";
    private static String SITE_ID_2 = "g4fzn";
    private static String DIGEST_1 = "b9bfc7692f901e306acd56cca80bb65a";
    private static String DIGEST_2 = "2885ef478b00f45d20927b7aaf0da119";
    private static String HEX_FRAME_FC1 = "8010A9109D10870113206400DB040700001918191800000301F4C853CE93A228EA23332" +
            "233A9EB6C329321852D772BF9393F114313000FE487483983D7ABA5295A5A93112F1183110D00C7205A00D78608228967" +
            "C7A428FA4E8C110210AE11930103206400D78398048A87C1A328DA2E8C110210E610F100F9205A00DB83B7FB86879FA26" +
            "8AA3287112F110D111E00E8206400DB8458057EF784A328FA468C104F105A104A00F8206400D787681D7B376CA568FA66" +
            "8C10F11060106600F9206400D7853811808782A468DA568910AE109210A300FB206400D78397E4847787A4287A3A8710B" +
            "4104410D500F6206400DB8107D386B796A2687A2684104F101711";
    private User user1;
    private User user2;
    private SatelliteStatus satelliteStatus;
    private List<SatelliteStatus> satelliteStatuses = new ArrayList<>();
    private List<HexFrame> hexFrames1 = new ArrayList<>();
    private List<HexFrame> hexFrames2 = new ArrayList<>();
    private Set<User> users1 = new HashSet<>();
    private Set<User> users2 = new HashSet<>();
    private List<UserRanking> rankings = new ArrayList<>();


    @Before
    public void setup() {
        mockUserDao = Mockito.mock(UserDao.class);
        mockHexFrameDao = Mockito.mock(HexFrameDao.class);
        mockClock = Mockito.mock(UTCClock.class);
        mockSatelliteStatusDao = Mockito.mock(SatelliteStatusDao.class);
        mockUserRankingDao = Mockito.mock(UserRankingDao.class);
        mockEnvConfig = Mockito.mock(EnvConfig.class);
        hexFrameService = new HexFrameServiceImpl(mockHexFrameDao, mockUserDao, mockClock, mockSatelliteStatusDao, mockUserRankingDao, mockEnvConfig);

        user1 = new User();
        user1.setSiteId(SITE_ID_1);
        user1.setAuthKey("foo");
        users1.add(user1);

        user2 = new User();
        user2.setSiteId(SITE_ID_2);
        user2.setAuthKey("bar");
        users2.add(user2);

        // an satelliteStatus for Funcube1
        satelliteStatus = new SatelliteStatus();
        satelliteStatus.setSatelliteId(2L);
        satelliteStatus.setEpochReferenceTime(new Timestamp(0));
        satelliteStatus.setEpochSequenceNumber(1L);
        satelliteStatus.setCatalogueNumber(39444L);
        satelliteStatuses.add(satelliteStatus);

        HexFrame hexFrame = new HexFrame();
        hexFrame.setId(1L);
        hexFrame.setUsers(users1);
        hexFrame.setCreatedDate(Calendar.getInstance().getTime());
        hexFrame.setFrameType(0L);
        hexFrame.setSequenceNumber(1131283L);
        hexFrame.setHexString(HEX_FRAME_FC1);
        user1.addFrame(hexFrame);
        hexFrames1.add(hexFrame);

        UserRanking userRanking = new UserRanking();
        userRanking.setSiteId(user1.getSiteId());
        rankings.add(userRanking);
    }

    @Test
    public void userNotFound() {
        when(mockUserDao.findBySiteId(SITE_ID_1)).thenReturn(null);
        final ResponseEntity responseEntity = hexFrameService.processHexFrame(SITE_ID_1, DIGEST_1, HEX_FRAME_FC1);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
    }

    @Test
    public void userWithBadDigest() {
        when(mockUserDao.findBySiteId(SITE_ID_1)).thenReturn(user1);
        final ResponseEntity responseEntity = hexFrameService.processHexFrame(SITE_ID_1, DIGEST_1, "nonsense");
        assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
    }

    @Test
    public void userWithBadDigestUsingCache() {
        when(mockUserDao.findBySiteId(SITE_ID_1)).thenReturn(user1);
        hexFrameService.processHexFrame(SITE_ID_1, "DIGEST", HEX_FRAME_FC1);
        final ResponseEntity responseEntity = hexFrameService.processHexFrame(SITE_ID_1, DIGEST_1, "nonsense");
        assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
    }

    @Test
    public void noValidSatelliteStatus() {
        when(mockUserDao.findBySiteId(SITE_ID_1)).thenReturn(user1);
        when(mockClock.currentDate()).thenReturn(Calendar.getInstance().getTime());
        when(mockSatelliteStatusDao.findAll()).thenReturn(Collections.EMPTY_LIST);
        final ResponseEntity responseEntity = hexFrameService.processHexFrame(SITE_ID_1, DIGEST_1, HEX_FRAME_FC1);
        assertEquals((HttpStatus.BAD_REQUEST.value()), responseEntity.getStatusCode().value());
    }

    @Test
    public void outOfBounds() {
        when(mockUserDao.findBySiteId(SITE_ID_1)).thenReturn(user1);
        when(mockClock.currentDate()).thenReturn(Calendar.getInstance().getTime());
        when(mockSatelliteStatusDao.findAll()).thenReturn(satelliteStatuses);
        when(mockHexFrameDao.getMaxSequenceNumber(2L)).thenReturn(new Long(1131283 + 1441));
        final ResponseEntity responseEntity = hexFrameService.processHexFrame(SITE_ID_1, DIGEST_1, HEX_FRAME_FC1);
        assertEquals((HttpStatus.ALREADY_REPORTED.value()), responseEntity.getStatusCode().value());
    }

    @Test
    public void processNewHexFrame() {
        when(mockUserDao.findBySiteId(SITE_ID_1)).thenReturn(user1);
        when(mockClock.currentDate()).thenReturn(Calendar.getInstance().getTime());
        when(mockSatelliteStatusDao.findAll()).thenReturn(satelliteStatuses);
        when(mockHexFrameDao.findBySatelliteIdAndSequenceNumberAndFrameType(2L, 1131283L, 0L))
                .thenReturn(Collections.EMPTY_LIST);
        when(mockUserRankingDao.findBySatelliteIdAndSiteId(2L, user1.getSiteId())).thenReturn(Collections.EMPTY_LIST);
        when(mockEnvConfig.satpredictURL()).thenReturn("http://satpredict.badgersoft.com");
        final ResponseEntity responseEntity = hexFrameService.processHexFrame(SITE_ID_1, DIGEST_1, HEX_FRAME_FC1);
        assertEquals((HttpStatus.OK.value()), responseEntity.getStatusCode().value());
    }

    @Test
    public void processExistingHexFrameWithNewUser() {
        when(mockUserDao.findBySiteId(SITE_ID_2)).thenReturn(user2);
        when(mockClock.currentDate()).thenReturn(Calendar.getInstance().getTime());
        when(mockSatelliteStatusDao.findAll()).thenReturn(satelliteStatuses);
        when(mockHexFrameDao.findBySatelliteIdAndSequenceNumberAndFrameType(2L, 1131283L, 0L))
                .thenReturn(hexFrames1);
        final ResponseEntity responseEntity = hexFrameService.processHexFrame(SITE_ID_2, DIGEST_2, HEX_FRAME_FC1);
        when(mockUserRankingDao.findBySatelliteIdAndSiteId(2L, user2.getSiteId())).thenReturn(Collections.EMPTY_LIST);
        when(mockEnvConfig.satpredictURL()).thenReturn("http://satpredict.badgersoft.com");
        assertEquals((HttpStatus.OK.value()), responseEntity.getStatusCode().value());
    }

    @Test
    public void processExistingHexFrameWithExistingUserNotReportedBefore() {
        when(mockUserDao.findBySiteId(SITE_ID_1)).thenReturn(user1);
        when(mockClock.currentDate()).thenReturn(Calendar.getInstance().getTime());
        when(mockSatelliteStatusDao.findAll()).thenReturn(satelliteStatuses);
        when(mockHexFrameDao.findBySatelliteIdAndSequenceNumberAndFrameType(2L, 1131283L, 0L))
                .thenReturn(hexFrames2);
        when(mockHexFrameDao.findBySatelliteIdAndSequenceNumber(2L, 1131283L)).thenReturn(hexFrames2);
        when(mockUserRankingDao.findBySatelliteIdAndSiteId(2L, user2.getSiteId())).thenReturn(rankings);
        when(mockEnvConfig.satpredictURL()).thenReturn("http://satpredict.badgersoft.com");
        final ResponseEntity responseEntity = hexFrameService.processHexFrame(SITE_ID_1, DIGEST_1, HEX_FRAME_FC1);
        assertEquals((HttpStatus.OK.value()), responseEntity.getStatusCode().value());
    }

    @Test
    public void processExistingHexFrameWithExistingUsereportedBefore() {
        when(mockUserDao.findBySiteId(SITE_ID_1)).thenReturn(user1);
        when(mockClock.currentDate()).thenReturn(Calendar.getInstance().getTime());
        when(mockSatelliteStatusDao.findAll()).thenReturn(satelliteStatuses);
        when(mockHexFrameDao.findBySatelliteIdAndSequenceNumberAndFrameType(2L, 1131283L, 0L))
                .thenReturn(hexFrames1);
        when(mockUserRankingDao.findBySatelliteIdAndSiteId(2L, user2.getSiteId())).thenReturn(rankings);
        when(mockEnvConfig.satpredictURL()).thenReturn("http://satpredict.badgersoft.com");
        final ResponseEntity responseEntity = hexFrameService.processHexFrame(SITE_ID_1, DIGEST_1, HEX_FRAME_FC1);
        assertEquals((HttpStatus.ALREADY_REPORTED.value()), responseEntity.getStatusCode().value());
    }
}
