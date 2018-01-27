package org.debugroom.wedding.app.web.portal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
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
import org.debugroom.wedding.app.model.portal.AnswerRequest;
import org.debugroom.wedding.app.model.portal.ConfirmInformation;
import org.debugroom.wedding.app.model.portal.Information;
import org.debugroom.wedding.app.model.portal.PortalResource;
import org.debugroom.wedding.domain.entity.Menu;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.model.portal.PortalInfoOutput;
import org.debugroom.wedding.domain.service.common.MenuSharedService;
import org.debugroom.wedding.domain.service.portal.PortalService;

@RestController
@RequestMapping("/api/v1")
public class PortalRestController {

	@Inject
	Mapper mapper;
	
	@Inject
	PortalService portalService;
	
	@Inject
	MenuSharedService menuSharedService;
	
	@RequestMapping(method=RequestMethod.GET, value="/portal/{userId:[0-9]+}")
	@ResponseStatus(HttpStatus.OK)
	public PortalResource getPortalResource(@PathVariable String userId) 
			throws MappingException, BusinessException{
		
		PortalInfoOutput output = portalService.getPortalInfo(
				User.builder().userId(userId).build());

		return PortalResource.builder()
				.user(output.getUser())
				.isUnWatched(output.isUnWatched())
				.informationList(output.getInformationList())
				.isNotAnswered(output.isNotAnswered())
				.requestList(output.getRequestList())
				.build();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/profile/{userId:[0-9]+}")
	@ResponseStatus(HttpStatus.OK)
	public User getProfile(@PathVariable String userId) throws BusinessException{
		return portalService.getUser(userId);
	}

	@RequestMapping(method=RequestMethod.GET, value="/information/{infoId:[0-9]+}")
	@ResponseStatus(HttpStatus.OK)
	public Information information(@Validated Information information){
		return mapper.map(portalService.getInformation(information.getInfoId()),
				Information.class);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/notification/{infoId:[0-9]+}/{userId:[0-9]+}")
	public boolean updateNotification(@PathVariable String infoId,
			@PathVariable String userId, @RequestBody ConfirmInformation confirmInformation){
		return portalService.updateNotification(infoId, userId, 
				confirmInformation.isWatched());
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{userId:[0-9]+}/request/{requestId:[0-9]+}")
	@ResponseStatus(HttpStatus.OK)
	public boolean updateRequestStatus(@PathVariable String userId,
			@PathVariable String requestId, @RequestBody AnswerRequest answerRequest){
		return portalService.updateRequestStatus(userId, requestId, 
				answerRequest.isApproved());
	}

	@RequestMapping(method=RequestMethod.GET, value="/{userId:[0-9]+}/menu")
	public List<org.debugroom.wedding.app.model.portal.Menu> getUsableMenu(
			@PathVariable String userId) throws BusinessException{
		List<Menu> menuList = menuSharedService.getUsableMenu(User.builder().userId(userId).build());
		/*
		 ResponseEntityで、boolean型はisXXXXという命名形式でなければ、シリアライズ対象から外れる模様。 
		 シリアライズされるようにオブジェクトをマッピングする。
		 */
		List<org.debugroom.wedding.app.model.portal.Menu> newMenuList =
				new ArrayList<org.debugroom.wedding.app.model.portal.Menu>();
		for(Menu menu : menuList){
			newMenuList.add(org.debugroom.wedding.app.model.portal.Menu.builder()
					.menuId(menu.getMenuId())
					.menuName(menu.getMenuName())
					.authorityLevel(menu.getAuthorityLevel())
					.pathvariables(menu.hasPathvariables())
					.url(menu.getUrl())
					.usableStartDate(menu.getUsableStartDate())
					.usableEndDate(menu.getUsableEndDate())
					.build());
		}
		return newMenuList;
	}

}
