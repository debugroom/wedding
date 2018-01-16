package org.debugroom.wedding.app.web.profile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.ResultActions.*;

import org.debugroom.wedding.app.model.profile.Address;
import org.debugroom.wedding.app.model.profile.Credential;
import org.debugroom.wedding.app.model.profile.CredentialPK;
import org.debugroom.wedding.app.model.profile.EditProfileForm;
import org.debugroom.wedding.app.model.profile.Email;
import org.debugroom.wedding.app.model.profile.EmailPK;
import org.debugroom.wedding.app.model.profile.User;
import org.debugroom.wedding.config.WebApp;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
	@ContextConfiguration(classes = WebApp.class)
})
@WebAppConfiguration
@ActiveProfiles({"junit", "jpa"})
public class ProfileRestControllerTest {

	@Autowired
	WebApplicationContext context;
	
	MockMvc mockMvc;
	
	@Before
	public void setUpMockMvc(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testProfilePortal() throws Exception{
		mockMvc.perform(get("/api/v1/profile/00000000"))
		.andExpect(status().isOk())
		.andDo(log());
	}

	@Test
	public void testEditProfile() throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		List<Email> emails = new ArrayList<Email>();
		List<Credential> credentials = new ArrayList<Credential>();
		Email email = new Email();
		Credential credential = new Credential();
		emails.add(Email.builder().id(new EmailPK("00000000", 0))
				.email("test1@test.com")
				.build());
		emails.add(Email.builder().id(new EmailPK("00000000", 1))
				.email("test2@test.com")
				.build());
		credentials.add(Credential.builder()
				.id(new CredentialPK("00000000","PASSWORD"))
				.credentialKey("password").build());
		EditProfileForm form = EditProfileForm
								.builder().user(User.builder()
													.userId("00000000")
													.imageFilePath("test.path")
													.loginId("test.loginId")
													.firstName("(ΦωΦ)")
													.lastName("ふぉふぉふぉ")
													.emails(emails)
													.credentials(credentials)
													.address(Address.builder()
															.postCd("000-1234")
															.address("Nagasaki")
															.build())
											.build())
								.build();
		mockMvc.perform(
				put("/api/v1/profile/00000000")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(form)))
		.andDo(log());
	}
}

