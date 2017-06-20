package org.debugroom.wedding.app.web.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

public class RequestBuilder {

	private static final String PROTOCOL = "http://";
	
	public static String getGetRequest(
			String path, String appName, String serviceName, 
			Map<String, String> paramMap, Map<Integer, String> pathVariableMap) 
					throws URISyntaxException{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(PROTOCOL)
						.append(path)
						.append("/")
						.append(appName)
						.append("/")
						.append(serviceName);

		if(pathVariableMap != null){
			for(Entry<Integer, String> entry : pathVariableMap.entrySet()){
				stringBuilder.append("/")
								.append(entry.getValue());
			}
		}

		int count = 0;
		if(paramMap != null){
			for(Entry<String, String> entry : paramMap.entrySet()){
				if(count == 0){
					stringBuilder.append("?");
				}else{
					stringBuilder.append("&");
				}
				stringBuilder.append(entry.getKey())
								.append("=")
								.append(entry.getValue());
				count++;
			}
		}
		return new URI(stringBuilder.toString()).toString();
	}
	
	public static String getRequestContextPath(HttpServletRequest request) 
			throws MalformedURLException, URISyntaxException{
		URL url = new URL(request.getRequestURL().toString());
		return new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
						url.getPort(), request.getContextPath(),null, null)
					.toString();
	}
}
