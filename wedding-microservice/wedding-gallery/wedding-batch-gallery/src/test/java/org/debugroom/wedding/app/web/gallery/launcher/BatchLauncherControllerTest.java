package org.debugroom.wedding.app.web.gallery.launcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.debugroom.wedding.app.batch.gallery.model.DownloadMedia;
import org.debugroom.wedding.app.batch.gallery.model.Movie;
import org.debugroom.wedding.app.batch.gallery.model.Photo;
import org.debugroom.wedding.config.web.WebApp;
import org.debugroom.wedding.domain.model.gallery.Media;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.ResultActions.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebApp.class)
@WebAppConfiguration
@ActiveProfiles({"dev", "jpa", "web"})
@Slf4j
public class BatchLauncherControllerTest {

	@Autowired
	WebApplicationContext context;
	
	MockMvc mockMvc;
	
	@Before
	public void setUpMockMvc(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testExecuteBatch() throws Exception{
		
		List<Photo> photographs = new ArrayList<Photo>();
		List<Movie> movies = new ArrayList<Movie>();
		DownloadMedia downloadMedia = 
				DownloadMedia.builder().photographs(photographs).movies(movies).build();
		photographs.add(Photo.builder().photoId("0000000000").build());
		photographs.add(Photo.builder().photoId("0000000001").build());
		photographs.add(Photo.builder().photoId("0000000002").build());
		movies.add(Movie.builder().movieId("0000000000").build());
		movies.add(Movie.builder().movieId("0000000001").build());

		ObjectMapper objectMapper = new ObjectMapper();
		String requestJsonString = objectMapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(downloadMedia);
		
		MvcResult mvcResult = mockMvc.perform(post("/api/v1/gallery/archive")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(requestJsonString.getBytes()))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

	}

}
