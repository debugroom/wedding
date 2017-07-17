package org.debugroom.wedding.app.web.management;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.model.common.UpdateResult;
import org.debugroom.wedding.domain.service.management.UserManagementService;

@RestController
@RequestMapping("/api/v1")
public class ManagementRestController {

	@Inject
	Mapper mapper;
	
	@Inject
	UserManagementService userManagementService;
	
	@RequestMapping(method=RequestMethod.GET, value="/users")
	public Page<User> getUsers(@PageableDefault(
			page=0, size=5, direction = Direction.ASC, sort={"userId"}) Pageable pageable){
		return userManagementService.getUsersUsingPage(pageable);
	}

	@RequestMapping(method=RequestMethod.GET, value="/users/{loginId:.+}")
	public User getUserByLoginId(@PathVariable String loginId) {
		try {
			return userManagementService.getUser(loginId);
		} catch (BusinessException e) {
			return User.builder().build(); 
		}
	}


	@RequestMapping(method=RequestMethod.POST, value="/user/profile/new")
	public User createUserProfile(@RequestBody 
			org.debugroom.wedding.app.model.management.User user) 
			throws MappingException, BusinessException{
		return userManagementService.createUserProfile(
				mapper.map(user, User.class));
	}

	@RequestMapping(method=RequestMethod.POST, value="/user/new")
	public User saveUser(@RequestBody
			org.debugroom.wedding.app.model.management.User user) 
					throws MappingException, BusinessException{
		return userManagementService.saveUser(
				mapper.map(user, User.class));
	}

	@RequestMapping(method=RequestMethod.GET, value="/user/{userId}")
	public User getUser(@PathVariable String userId) throws BusinessException{
		return userManagementService.getUserProfile(userId);
	}

	@RequestMapping(method=RequestMethod.PUT, value="/user/{userId}")
	public UpdateResult<User> updateUser(@RequestBody
			org.debugroom.wedding.app.model.management.User user) 
					throws MappingException, BusinessException{
		return userManagementService.udpateUser(
				mapper.map(user, User.class));
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/user/{userId}")
	public User deleteUser(@RequestBody
			org.debugroom.wedding.app.model.management.User user){
		User deleteUser = userManagementService.deleteUser(user.getUserId());
		return deleteUser;
	}
}
