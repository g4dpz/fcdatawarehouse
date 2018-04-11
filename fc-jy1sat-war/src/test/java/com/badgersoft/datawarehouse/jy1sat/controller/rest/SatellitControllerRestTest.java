package com.badgersoft.datawarehouse.jy1sat.controller.rest;

import com.badgersoft.datawarehouse.common.services.SatelliteService;
import com.badgersoft.datawarehouse.jy1sat.config.AppConfig;
import com.badgersoft.datawarehouse.jy1sat.config.TestJpaConfig;
import com.badgersoft.datawarehouse.jy1sat.service.SatelliteServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class, TestJpaConfig.class},
        loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class SatellitControllerRestTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private SatelliteService mockSatelliteService;

    @Before
    public void setUp() throws Exception {
        mockSatelliteService = mock(SatelliteServiceImpl.class);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void ping() throws Exception {
        when(mockSatelliteService.ping()).thenReturn("OK");
        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));
    }


}
