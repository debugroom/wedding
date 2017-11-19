package org.debugroom.wedding.app.web.profile;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.dozer.Mapper;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.model.profile.EditProfileForm;
import org.debugroom.wedding.domain.entity.Address;
import org.debugroom.wedding.domain.entity.Credential;
import org.debugroom.wedding.domain.entity.CredentialPK;
import org.debugroom.wedding.domain.entity.Email;
import org.debugroom.wedding.domain.entity.EmailPK;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.model.common.UpdateResult;
import org.debugroom.wedding.domain.service.profile.ProfileService;

@RestController
@RequestMapping("/api/v1")
public class ProfileRestController {

	@Inject
	Mapper mapper;
	
	@Inject
	ProfileService profileService;
	
	
	@RequestMapping(method=RequestMethod.GET, value="/profile/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public User profilePortal(@PathVariable String userId) 
			throws BusinessException{
// 		return profileService.getUserProfile(userId);
 		User user =  profileService.getUserProfile(userId);
 		return user;
	}

	@RequestMapping(method = RequestMethod.PUT, value="/profile/{userId}", 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public UpdateResult<User> editProfile(@PathVariable String userId, 
			@RequestBody EditProfileForm editProfileForm) throws BusinessException{
//		return profileService.updateUserProfile(
//				mapper.map(editProfileForm.getUser(), User.class));
		Set<Email> emails = new HashSet<Email>();
		Set<Credential> credentials = new HashSet<Credential>();
		if(editProfileForm.getUser().getEmails() != null 
				&& editProfileForm.getUser().getEmails().size() != 0){
			for(org.debugroom.wedding.app.model.profile.Email email 
					: editProfileForm.getUser().getEmails()){
				emails.add(Email.builder()
						.id(EmailPK.builder()
								.userId(email.getId().getUserId())
								.emailId(email.getId().getEmailId())
								.build())
						.email(email.getEmail())
						.build());
			}
		}
		if(editProfileForm.getUser().getCredentials() != null
				&& editProfileForm.getUser().getCredentials().size() != 0){
			for(org.debugroom.wedding.app.model.profile.Credential credential 
					: editProfileForm.getUser().getCredentials()){
				credentials.add(Credential.builder()
						.id(CredentialPK.builder()
								.credentialType(credential.getId().getCredentialType())
								.userId(credential.getId().getUserId())
								.build())
						.credentialKey(credential.getCredentialKey())
						.build());
			}
			
		}
		
		return profileService.updateUserProfile(
				User.builder()
				.userId(editProfileForm.getUser().getUserId())
				.lastName(editProfileForm.getUser().getLastName())
				.firstName(editProfileForm.getUser().getFirstName())
				.loginId(editProfileForm.getUser().getLoginId())
				.imageFilePath(editProfileForm.getUser().getImageFilePath())
				.address(Address.builder()
						.postCd(editProfileForm.getUser().getAddress().getPostCd())
						.address(editProfileForm.getUser().getAddress().getAddress())
						.build())
				.emails(emails)
				.credentials(credentials)
				.build());
	}
}
