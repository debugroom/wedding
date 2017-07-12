package org.debugroom.wedding.app.web.profile;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.dozer.Mapper;
import org.dozer.MappingException;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.model.profile.EditProfileForm;
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
		return profileService.updateUserProfile(
				mapper.map(editProfileForm.getUser(), User.class));
	}
}
