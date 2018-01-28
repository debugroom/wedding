package org.debugroom.wedding.app.web.security;

import javax.inject.Inject;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import org.debugroom.wedding.domain.service.common.UserSharedService;

@Component
public class SessionDestroyedListener implements ApplicationListener<SessionDestroyedEvent>{

	@Inject
	UserSharedService userSharedService;
	
	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {

		for(SecurityContext securityContext : event.getSecurityContexts()){
			Authentication authentication = securityContext.getAuthentication();
			CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();
			userSharedService.updateLastLoginDate(customUserDetails.getUser().getUserId());
		}
		
	}

}
