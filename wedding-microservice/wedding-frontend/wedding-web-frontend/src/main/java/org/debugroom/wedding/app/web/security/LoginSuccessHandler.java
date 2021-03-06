package org.debugroom.wedding.app.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.debugroom.wedding.domain.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class LoginSuccessHandler implements AuthenticationSuccessHandler{
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
			HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		User user = ((CustomUserDetails)auth.getPrincipal()).getUser();
		response.sendRedirect(new StringBuilder("portal/")
				.append(user.getUserId())
				.toString());
	}

}
