package org.debugroom.wedding.app.web.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.debugroom.wedding.app.web.adapter.docker.provider.ConnectPathProvider;
import org.debugroom.wedding.app.web.interceptor.HeaderRequestInterceptor;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class RequestBuilder {

	private static final String PROTOCOL = "http";
	private static final String APP_NAME = "api/v1";
	private static final String MDC_KEY_NAME = "X-Track";
	private static final String HEADER_USER_ID_KEY_NAME = "X-UserId";

	public static String getGetRequest(
			String path, String appName, String serviceName, 
			Map<String, String> paramMap, Map<Integer, String> pathVariableMap) 
					throws URISyntaxException{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getPathVariableHttpRequest(
				path, appName, serviceName, pathVariableMap));
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
	
	public static String getPutRequest(String path, String appName, String serviceName,
			Map<Integer, String> pathVariableMap) throws URISyntaxException{
		return getPathVariableHttpRequest(path, appName, serviceName, pathVariableMap);
	}
	
	public static String getPathVariableHttpRequest(
			String path, String appName, String serviceName,
			Map<Integer, String> pathVariableMap) throws URISyntaxException{
	
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getHttpRequest(path, appName, serviceName));
		if(pathVariableMap != null){
			for(Entry<Integer, String> entry : pathVariableMap.entrySet()){
				stringBuilder.append("/")
								.append(entry.getValue());
			}
		}
		return stringBuilder.toString();
	
	}
	
	public static URI getPathVariableHttpRequestURI(
			String path, String appName, String serviceName,
			Map<Integer, String> pathVariableMap) throws URISyntaxException{
		return new URI(getPathVariableHttpRequest(
				path, appName, serviceName, pathVariableMap));
	}

	public static String getHttpRequest(String path, String appName, String serviceName){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(PROTOCOL)
						.append(path)
						.append("/")
						.append(appName)
						.append("/")
						.append(serviceName);
		return stringBuilder.toString();
	}

	public static URI getHttpRequestURI(String path, String appName, String serviceName) 
			throws URISyntaxException{
		return new URI(getHttpRequest(path, appName, serviceName));
	}

	public static String getRequestContextPath(HttpServletRequest request) 
			throws MalformedURLException, URISyntaxException{
		URL url = new URL(request.getRequestURL().toString());
		return new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
						url.getPort(), request.getContextPath(),null, null)
					.toString();
	}
	
	public static UriComponents buildUriComponents(
			String serviceName, String path, ConnectPathProvider provider){

		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

		return uriComponentsBuilder.scheme(PROTOCOL)
				.host(provider.getIpAddr(serviceName))
				.port(provider.getPort(serviceName))
				.path(path)
				.build();
	
	}

	public static UriComponents buildUriComponents(
			String serviceName, String path, ConnectPathProvider provider, 
			MultiValueMap<String, String> params){
		
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

		return uriComponentsBuilder.scheme(PROTOCOL)
				.host(provider.getIpAddr(serviceName))
				.port(provider.getPort(serviceName))
				.path(path)
				.queryParams(params)
				.build();
	}

	public static UriComponents buildUriComponents(
			String protocol, String serviceName, String path, ConnectPathProvider provider,
			Map<String, String> uriVariables){
		if("https".equals(protocol)){
			return UriComponentsBuilder.newInstance()
				.scheme(protocol)
				.host(provider.getIpAddr(serviceName))
				.path(path)
				.buildAndExpand(uriVariables);
		}
		return UriComponentsBuilder.newInstance()
				.scheme(protocol)
				.host(provider.getIpAddr(serviceName))
				.port(provider.getPort(serviceName))
				.path(path)
				.buildAndExpand(uriVariables);
	}

	public static UriComponents buildUriComponents(
			String protocol, String serviceName, String path, ConnectPathProvider provider,
			Map<String, String> uriVariables, MultiValueMap<String, String> params){
		if("https".equals(protocol)){
			return UriComponentsBuilder.newInstance()
				.scheme(protocol)
				.host(provider.getIpAddr(serviceName))
				.path(path)
				.queryParams(params)
				.buildAndExpand(uriVariables);
		}
		return UriComponentsBuilder.newInstance()
				.scheme(protocol)
				.host(provider.getIpAddr(serviceName))
				.port(provider.getPort(serviceName))
				.path(path)
				.queryParams(params)
				.buildAndExpand(uriVariables);
	}
	
	public static String getServicePath(String serviceName,
			String path, ConnectPathProvider provider,
			MultiValueMap<String, String> params){
		
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.scheme(PROTOCOL)
				.host(provider.getIpAddr(serviceName))
				.port(provider.getPort(serviceName))
				.path(path)
				.queryParams(params)
				.build();
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(uriComponents.getPath());
		if(null != uriComponents.getQuery()){
			stringBuilder.append("?").append(uriComponents.getQuery());
		}
		return stringBuilder.toString();
	}

	public static String getServicePath(String serviceName,
			String path, ConnectPathProvider provider,
			Map<String, String> uriVariables){
		
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.scheme(PROTOCOL)
				.host(provider.getIpAddr(serviceName))
				.port(provider.getPort(serviceName))
				.path(path)
				.buildAndExpand(uriVariables);
		
		return uriComponents.getPath();
	}
	
	public static String getServicePath(String serviceName,
			String path, ConnectPathProvider provider,
			Map<String, String> uriVariables, MultiValueMap<String, String> params){
		
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.scheme(PROTOCOL)
				.host(provider.getIpAddr(serviceName))
				.port(provider.getPort(serviceName))
				.path(path)
				.queryParams(params)
				.buildAndExpand(uriVariables);
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(uriComponents.getPath());
		if(null != uriComponents.getQuery()){
			stringBuilder.append("?").append(uriComponents.getQuery());
		}
		return stringBuilder.toString();
	}

	public static RestTemplate getMDCLoggableRestTemplate(){
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new HeaderRequestInterceptor(MDC_KEY_NAME, MDC.get(MDC_KEY_NAME)));
		interceptors.add(new HeaderRequestInterceptor(HEADER_USER_ID_KEY_NAME, MDC.get(HEADER_USER_ID_KEY_NAME)));
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setInterceptors(interceptors);
		return restTemplate;
	}

	public static HttpHeaders getMDCLoggableHttpHeaders(){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(MDC_KEY_NAME, MDC.get(MDC_KEY_NAME));
		httpHeaders.add(HEADER_USER_ID_KEY_NAME, MDC.get(HEADER_USER_ID_KEY_NAME));
		return httpHeaders;
	}
}
