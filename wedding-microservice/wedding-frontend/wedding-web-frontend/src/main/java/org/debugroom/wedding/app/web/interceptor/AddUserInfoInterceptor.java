package org.debugroom.wedding.app.web.interceptor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

import org.debugroom.wedding.app.web.security.CustomUserDetails;
import org.debugroom.wedding.domain.entity.User;

@Slf4j
public class AddUserInfoInterceptor extends HandlerInterceptorAdapter{


	@Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
		if(null != modelAndView){
			if(null == modelAndView.getModel().get("user")){
				SecurityContext securityContext = SecurityContextHolder.getContext();
				Authentication authentication = securityContext.getAuthentication();
				Object principal = authentication.getPrincipal();
				if(principal instanceof CustomUserDetails){
					modelAndView.addObject("user", 
							((CustomUserDetails)authentication.getPrincipal()).getUser());
				}else{
					modelAndView.addObject("user", 
							User.builder().userId("00000000").authorityLevel(0).build());
				}
			}
		}
    }

}
