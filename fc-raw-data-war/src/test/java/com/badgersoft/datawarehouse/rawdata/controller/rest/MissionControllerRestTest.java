package com.badgersoft.datawarehouse.rawdata.controller.rest;

import com.badgersoft.datawarehouse.rawdata.service.HexFrameService;
import com.badgersoft.datawarehouse.rawdata.config.AppConfig;
import com.badgersoft.datawarehouse.rawdata.config.TestJpaConfig;
import com.badgersoft.datawarehouse.rawdata.service.impl.HexFrameServiceImpl;
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
public class MissionControllerRestTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private HexFrameService mockHexFrameService;

    @Before
    public void setUp() throws Exception {
        mockHexFrameService = mock(HexFrameServiceImpl.class);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void ping() throws Exception {
        when(mockHexFrameService.ping()).thenReturn("OK");
        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));
    }


}
