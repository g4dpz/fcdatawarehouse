package com.badgersoft.datawarehouse.rawdata.service;

import com.badgersoft.datawarehouse.common.services.HexFrameService;
import com.badgersoft.datawarehouse.rawdata.config.AppConfig;
import com.badgersoft.datawarehouse.rawdata.config.JpaConfig;
import com.badgersoft.datawarehouse.rawdata.dao.UserDao;
import com.badgersoft.datawarehouse.rawdata.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class, JpaConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class HexFrameServiceImplTest {

    HexFrameService hexFrameService = new HexFrameServiceImpl();

    @Resource
    private UserDao userDao;

    @Test
    public void process() {
        User user = new User();
        user.setId(1L);
        userDao.save(user);

        User user2 = userDao.findOne(1L);
        assertEquals(1L, user2.getId().longValue());
    }
}
