package com.badgersoft.datawarehouse.rawdata.service;

import com.badgersoft.datawarehouse.common.services.HexFrameService;
import com.badgersoft.datawarehouse.common.utils.Clock;
import com.badgersoft.datawarehouse.common.utils.UTCClock;
import com.badgersoft.datawarehouse.rawdata.config.AppConfig;
import com.badgersoft.datawarehouse.rawdata.config.TestJpaConfig;
import com.badgersoft.datawarehouse.rawdata.dao.HexFrameDao;
import com.badgersoft.datawarehouse.rawdata.dao.UserDao;
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

import java.util.Calendar;

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

    private static String SITE_ID = "g4dpz";
    private static String DIGEST = "e51cfc26d6b9bf379bb4d6eb28b07afd";
    private static String HEX_FRAME = "yyyy";
    private User user;


    @Before
    public void setup() {
        mockUserDao = Mockito.mock(UserDao.class);
        mockHexFrameDao = Mockito.mock(HexFrameDao.class);
        mockClock = Mockito.mock(UTCClock.class);
        hexFrameService = new HexFrameServiceImpl(mockHexFrameDao, mockUserDao, mockClock);
        user = new User();
        user.setSiteId("g4dpz");
        user.setAuthKey("foo");
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
    public void validUserAndDigest() {
        when(mockUserDao.findBySiteId(SITE_ID)).thenReturn(user);
        when(mockClock.currentDate()).thenReturn(Calendar.getInstance().getTime());
        hexFrameService.processHexFrame(SITE_ID, DIGEST, HEX_FRAME);
        final ResponseEntity responseEntity = hexFrameService.processHexFrame(SITE_ID, DIGEST, HEX_FRAME);
        assertEquals((HttpStatus.OK.value()), responseEntity.getStatusCode().value());
    }
}
