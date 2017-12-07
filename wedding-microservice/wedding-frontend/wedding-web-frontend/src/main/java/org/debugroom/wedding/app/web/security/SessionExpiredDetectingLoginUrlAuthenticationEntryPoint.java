package org.debugroom.wedding.app.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.RedirectUrlBuilder;

public class SessionExpiredDetectingLoginUrlAuthenticationEntryPoint
                 extends LoginUrlAuthenticationEntryPoint {

	@Value("${protocol}")
	private String protocol;
	
	public SessionExpiredDetectingLoginUrlAuthenticationEntryPoint(
			String loginFormUrl) {
		super(loginFormUrl);
	}

	@Override
	protected String buildRedirectUrlToLoginPage(HttpServletRequest request, 
			HttpServletResponse response, AuthenticationException authException) {
 
		String redirectUrl = super.buildRedirectUrlToLoginPage(request, response, authException);
		if (isRequestedSessionInvalid(request)) {
			if("https".equals(protocol)){
				RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
				urlBuilder.setScheme("https");
				urlBuilder.setServerName(request.getServerName());
				urlBuilder.setContextPath(request.getContextPath());
				urlBuilder.setPathInfo("/login");
				redirectUrl = urlBuilder.getUrl();
			}
		}
		return redirectUrl;
	}
 
	private boolean isRequestedSessionInvalid(HttpServletRequest request) {
		return request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid();
	}
}
