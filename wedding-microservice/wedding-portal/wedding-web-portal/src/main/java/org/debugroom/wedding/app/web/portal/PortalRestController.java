package org.debugroom.wedding.app.web.portal;

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
import org.debugroom.wedding.app.model.portal.Information;
import org.debugroom.wedding.app.model.portal.PortalResource;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.model.portal.PortalInfoOutput;
import org.debugroom.wedding.domain.service.portal.PortalService;

@RestController
@RequestMapping("/api/v1")
public class PortalRestController {

	@Inject
	Mapper mapper;
	
	@Inject
	PortalService portalService;
	
	@RequestMapping(method=RequestMethod.GET, value="/portal/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public PortalResource getPortalResource(@PathVariable String userId) 
			throws MappingException, BusinessException{
		
		PortalInfoOutput output = portalService.getPortalInfo(
				User.builder().userId(userId).build());

		return PortalResource.builder()
				.user(output.getUser())
				.informationList(output.getInformationList())
				.build();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/profile/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public User getProfile(@PathVariable String userId) throws BusinessException{
		return portalService.getUser(userId);
	}

	@RequestMapping(method=RequestMethod.GET, value="/information/{infoId}")
	@ResponseStatus(HttpStatus.OK)
	public Information information(@Validated Information information){
		return mapper.map(portalService.getInformation(information.getInfoId()),
				Information.class);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{userId:[0-9]+}/request/{requestId:[0-9]+}")
	@ResponseStatus(HttpStatus.OK)
	public boolean updateRequestStatus(@PathVariable String userId,
			@PathVariable String requestId, @RequestBody AnswerRequest answerRequest){
		return portalService.updateRequestStatus(userId, requestId, 
				answerRequest.isApproved());
	}

}
