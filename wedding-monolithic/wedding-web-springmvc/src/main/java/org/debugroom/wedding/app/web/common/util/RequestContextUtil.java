package org.debugroom.wedding.app.web.common.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

public class RequestContextUtil {

	public static String getRequestContextPath(HttpServletRequest request) 
			throws MalformedURLException, URISyntaxException{
		URL url = new URL(request.getRequestURL().toString());
		return new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
						url.getPort(), request.getContextPath(),null, null)
					.toString();
		
	}

}
