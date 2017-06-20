package org.debugroom.wedding.app.web.portal;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.dozer.Mapper;
import org.dozer.MappingException;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.model.portal.PortalResource;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.model.portal.PortalInfoOutput;
import org.debugroom.wedding.domain.service.portal.PortalService;

@RestController
@RequestMapping("/api/v1/portal")
public class PortalRestController {

	@Inject
	PortalService portalService;
	
	@RequestMapping(method=RequestMethod.GET, value="/{userId}")
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
	
	
}
