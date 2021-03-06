package com.badgersoft.datawarehouse.rawdata.dao;

import com.badgersoft.datawarehouse.rawdata.config.AppConfig;
import com.badgersoft.datawarehouse.rawdata.config.MessagingConfig;
import com.badgersoft.datawarehouse.rawdata.config.TestJpaConfig;
import com.badgersoft.datawarehouse.rawdata.domain.HexFrame;
import com.badgersoft.datawarehouse.rawdata.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class, MessagingConfig.class, TestJpaConfig.class},
        loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
@Transactional()
public class UserTest {

    @Resource
    private UserDao userDao;
    @Resource
    private HexFrameDao hexFrameDao;

    @Test
    public void create() {
        User user = new User();
        user.setId(1L);
        userDao.save(user);

        User user2 = userDao.findById(1L).get();
        assertEquals(1L, user2.getId().longValue());
    }

    @Test
    public void associateHexFrame() {

        User user1 = new User();
        user1 = userDao.save(user1);

        HexFrame hexFrame1 = new HexFrame();
        hexFrame1 = hexFrameDao.save(hexFrame1);

        user1.addFrame(hexFrame1);
        user1 = userDao.save(user1);

        List<HexFrame> frames = hexFrameDao.findAll();

        assertTrue(!frames.isEmpty());
        assertTrue(frames.get(0).getUsers().contains(user1));


    }
}
