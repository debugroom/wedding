package org.debugroom.wedding.app.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

import org.debugroom.wedding.domain.entity.User;

@Slf4j
public class AddUserInfoInterceptor extends HandlerInterceptorAdapter{

	@Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
		log.info(this.getClass() + "#postHandle() called.");
		if(null != modelAndView){
			if(null == modelAndView.getModel().get("user")){
				modelAndView.addObject(User
						.builder()
						.userId("00000000")
						.lastName("anonymous")
						.firstName("user")
						.authorityLevel(0)
						.build());
			}
		}
    }

}
