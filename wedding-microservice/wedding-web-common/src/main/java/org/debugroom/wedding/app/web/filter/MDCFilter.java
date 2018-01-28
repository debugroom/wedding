package org.debugroom.wedding.app.web.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

public class MDCFilter extends OncePerRequestFilter{

	private static final String MDC_KEY_NAME = "X-Track";
	private static final String HEADER_USER_ID_KEY_NAME = "X-UserId";

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String xTrack = request.getHeader(MDC_KEY_NAME);
		String userId = request.getHeader(HEADER_USER_ID_KEY_NAME);
		if(xTrack == null){
			xTrack = UUID.randomUUID().toString();
			request.setAttribute(MDC_KEY_NAME, xTrack); 
		}
		if(userId != null || !"".equals(userId)){
			MDC.put(HEADER_USER_ID_KEY_NAME, userId);
		}
		response.setHeader(MDC_KEY_NAME, xTrack);
		MDC.put(MDC_KEY_NAME, xTrack);
		try{
			filterChain.doFilter(request, response);
		} finally{
			MDC.remove(MDC_KEY_NAME);
			MDC.remove(HEADER_USER_ID_KEY_NAME);
		}
	}
}
