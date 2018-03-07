package com.badgersoft.datawarehouse.rawdata.service;

import com.badgersoft.datawarehouse.common.services.HexFrameService;
import com.badgersoft.datawarehouse.common.utils.Clock;
import com.badgersoft.datawarehouse.common.utils.UTCClock;
import com.badgersoft.datawarehouse.rawdata.config.AppConfig;
import com.badgersoft.datawarehouse.rawdata.config.TestJpaConfig;
import com.badgersoft.datawarehouse.rawdata.dao.EpochDao;
import com.badgersoft.datawarehouse.rawdata.dao.HexFrameDao;
import com.badgersoft.datawarehouse.rawdata.dao.UserDao;
import com.badgersoft.datawarehouse.rawdata.domain.Epoch;
import com.badgersoft.datawarehouse.rawdata.domain.User;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

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
    private EpochDao mockEpochDao;

    private static String SITE_ID = "g4dpz";
    private static String DIGEST = "b9bfc7692f901e306acd56cca80bb65a";
    private static String HEX_FRAME = "8010A9109D10870113206400DB040700001918191800000301F4C853CE93A228EA23332" +
            "233A9EB6C329321852D772BF9393F114313000FE487483983D7ABA5295A5A93112F1183110D00C7205A00D78608228967" +
            "C7A428FA4E8C110210AE11930103206400D78398048A87C1A328DA2E8C110210E610F100F9205A00DB83B7FB86879FA26" +
            "8AA3287112F110D111E00E8206400DB8458057EF784A328FA468C104F105A104A00F8206400D787681D7B376CA568FA66" +
            "8C10F11060106600F9206400D7853811808782A468DA568910AE109210A300FB206400D78397E4847787A4287A3A8710B" +
            "4104410D500F6206400DB8107D386B796A2687A2684104F101711";
    private User user;
    private Epoch epoch;
    private List<Epoch> epochs = new ArrayList<>();


    @Before
    public void setup() {
        mockUserDao = Mockito.mock(UserDao.class);
        mockHexFrameDao = Mockito.mock(HexFrameDao.class);
        mockClock = Mockito.mock(UTCClock.class);
        mockEpochDao = Mockito.mock(EpochDao.class);
        hexFrameService = new HexFrameServiceImpl(mockHexFrameDao, mockUserDao, mockClock, mockEpochDao);

        user = new User();
        user.setSiteId("g4dpz");
        user.setAuthKey("foo");

        // an epoch for Funcube1
        epoch = new Epoch();
        epoch.setSatelliteId(2L);
        epoch.setReferenceTime(new Timestamp(0));
        epoch.setSequenceNumber(1L);
        epochs.add(epoch);
    }

    @Test
    public void userNotFound() {
        when(mockUserDao.findBySiteId(SITE_ID)).thenReturn(null);
        final ResponseEntity responseEntity = hexFrameService.processHexFrame(SITE_ID, DIGEST, HEX_FRAME);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
    }

    @Test
    public void userWithBadDigest() {
        when(mockUserDao.findBySiteId(SITE_ID)).thenReturn(user);
        final ResponseEntity responseEntity = hexFrameService.processHexFrame(SITE_ID, DIGEST, "nonsense");
        assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
    }

    @Test
    public void userWithBadDigestUsingCache() {
        when(mockUserDao.findBySiteId(SITE_ID)).thenReturn(user);
        hexFrameService.processHexFrame(SITE_ID, "DIGEST", HEX_FRAME);
        final ResponseEntity responseEntity = hexFrameService.processHexFrame(SITE_ID, DIGEST, "nonsense");
        assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCode().value());
    }

    @Test
    public void noValidEpoch() {
        when(mockUserDao.findBySiteId(SITE_ID)).thenReturn(user);
        when(mockClock.currentDate()).thenReturn(Calendar.getInstance().getTime());
        when(mockEpochDao.findAll()).thenReturn(Collections.EMPTY_LIST);
        final ResponseEntity responseEntity = hexFrameService.processHexFrame(SITE_ID, DIGEST, HEX_FRAME);
        assertEquals((HttpStatus.BAD_REQUEST.value()), responseEntity.getStatusCode().value());
    }

    @Test
    public void outOfBounds() {
        when(mockUserDao.findBySiteId(SITE_ID)).thenReturn(user);
        when(mockClock.currentDate()).thenReturn(Calendar.getInstance().getTime());
        when(mockEpochDao.findAll()).thenReturn(epochs);
        when(mockHexFrameDao.getMaxSequenceNumber(2L)).thenReturn(new Long(1131283 + 1441));
        final ResponseEntity responseEntity = hexFrameService.processHexFrame(SITE_ID, DIGEST, HEX_FRAME);
        assertEquals((HttpStatus.ALREADY_REPORTED.value()), responseEntity.getStatusCode().value());
    }

    @Test
    public void validUserAndDigestAndExecute() {
        when(mockUserDao.findBySiteId(SITE_ID)).thenReturn(user);
        when(mockClock.currentDate()).thenReturn(Calendar.getInstance().getTime());
        when(mockEpochDao.findAll()).thenReturn(epochs);
        final ResponseEntity responseEntity = hexFrameService.processHexFrame(SITE_ID, DIGEST, HEX_FRAME);
        assertEquals((HttpStatus.OK.value()), responseEntity.getStatusCode().value());
    }
}
