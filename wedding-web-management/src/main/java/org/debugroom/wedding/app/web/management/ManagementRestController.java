package org.debugroom.wedding.app.web.management;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.dozer.Mapper;
import org.dozer.MappingException;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.model.management.information.InformationFormResource;
import org.debugroom.wedding.app.model.management.information.UserSearchResult;
import org.debugroom.wedding.domain.entity.Information;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.model.common.UpdateResult;
import org.debugroom.wedding.domain.model.management.InformationDetail;
import org.debugroom.wedding.domain.model.management.InformationDraft;
import org.debugroom.wedding.domain.service.management.InformationManagementService;
import org.debugroom.wedding.domain.service.management.UserManagementService;

@RestController
@RequestMapping("/api/v1")
public class ManagementRestController {

	@Inject
	Mapper mapper;
	
	@Inject
	UserManagementService userManagementService;
	
	@Inject
	InformationManagementService informationManagementService;
	
	@RequestMapping(method=RequestMethod.GET, value="/users")
	public Page<User> getUsers(@PageableDefault(
			page=0, size=10, direction=Direction.ASC, sort={"userId"}) Pageable pageable){
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
			org.debugroom.wedding.app.model.management.user.User user) 
			throws MappingException, BusinessException{
		return userManagementService.createUserProfile(
				mapper.map(user, User.class));
	}

	@RequestMapping(method=RequestMethod.POST, value="/user/{userId}")
	public User saveUser(@RequestBody
			org.debugroom.wedding.app.model.management.user.User user) 
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
			org.debugroom.wedding.app.model.management.user.User user) 
					throws MappingException, BusinessException{
		return userManagementService.udpateUser(
				mapper.map(user, User.class));
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/user/{userId}")
	public User deleteUser(@RequestBody
			org.debugroom.wedding.app.model.management.user.User user){
		return userManagementService.deleteUser(user.getUserId());
	}

	@RequestMapping(method=RequestMethod.GET, value="/information")
	public Page<Information> getInformation(@PageableDefault(
			page=0, size=10, direction=Direction.ASC, sort={"infoId"}) Pageable pageable){
		return informationManagementService.getInformationUsingPage(pageable);
	}

	@RequestMapping(method=RequestMethod.GET, value="/information/form")
	public InformationFormResource getInformationForm(){
		return InformationFormResource.builder().users(
				informationManagementService.getUsers()).build();
	}

	@RequestMapping(method=RequestMethod.POST, value="/information/draft/new")
	public InformationDraft createInformationDraft(@RequestBody 
			org.debugroom.wedding.app.model.management.information.Information information) 
					throws MappingException, BusinessException{
		return informationManagementService.createInformationDraft(
				mapper.map(information, InformationDraft.class));
		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/information/{infoId}")
	public Information saveInformation(@RequestBody
			org.debugroom.wedding.app.model.management.information.Information information)
					throws MappingException, BusinessException{
		return informationManagementService.saveInformation(
				mapper.map(information, InformationDraft.class));
	}

	@RequestMapping(method=RequestMethod.GET, value="/information/{infoId}")
	public InformationDetail getInformation(@PathVariable String infoId){
		return informationManagementService.getInformationDetail(infoId);
	}

	@RequestMapping(method=RequestMethod.GET, value="/users", 
			params="not-information-viewers")
	public UserSearchResult getNotInformationViewers(
			@RequestParam("infoId") String infoId){
		return UserSearchResult.builder()
				.infoId(infoId)
				.users(informationManagementService.getNoViewers(infoId))
				.build();
	}

	@RequestMapping(method=RequestMethod.PUT, value="/information/{infoId}")
	public UpdateResult<InformationDetail> updateInformation(@RequestBody
			org.debugroom.wedding.app.model.management.information.InformationDraft informationDraft) 
					throws MappingException, BusinessException{
		return informationManagementService.updateInformation(
				mapper.map(informationDraft, InformationDraft.class));
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/information/{info}")
	public Information deleteInformation(@RequestBody 
			org.debugroom.wedding.app.model.management.information.Information information){
		return informationManagementService.deleteInformation(information.getInfoId());
	}

}
