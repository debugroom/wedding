package org.debugroom.wedding.app.web.adapter.docker;

import org.debugroom.wedding.config.WebApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.ResultActions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebApp.class)
@WebAppConfiguration
@ActiveProfiles({"dev", "jpa"})
@Slf4j
public class ServiteAdapterControllerTest {

	@Autowired
	WebApplicationContext context;
	
	MockMvc mockMvc;
	
	@Before
	public void setUpMockMvc(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testGetPhoto() throws Exception{
		MvcResult mvcResult = mockMvc.perform(get("/gallery/photo/0000000031/xxx.png"))
		.andExpect(status().isOk())
		.andReturn();
		
		log.info(mvcResult.getResponse().getHeader("content-type"));
		

	}

}
