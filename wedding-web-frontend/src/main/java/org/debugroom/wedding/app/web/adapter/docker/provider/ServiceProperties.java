package org.debugroom.wedding.app.web.adapter.docker.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ServiceProperties {

	@Value("${frontend.service.name}")
	private String frontendServiceName;
	
	@Value("${frontend.service.addr}")
	private String frontendServiceAddr;

	@Value("${frontend.service.port}")
	private String frontendServicePort;

	@Value("${portal.service.name}")
	private String portalServiceName;
	
	@Value("${portal.service.addr}")
	private String portalServiceAddr;

	@Value("${portal.service.port}")
	private String portalServicePort;

	@Value("${information.service.name}")
	private String informationServiceName;
	
	@Value("${information.service.addr}")
	private String informationServiceAddr;

	@Value("${information.service.port}")
	private String informationServicePort;

	@Value("${profile.service.name}")
	private String profileServiceName;
	
	@Value("${profile.service.addr}")
	private String profileServiceAddr;

	@Value("${profile.service.port}")
	private String profileServicePort;

	@Value("${management.service.name}")
	private String managementServiceName;
	
	@Value("${management.service.addr}")
	private String managementServiceAddr;

	@Value("${management.service.port}")
	private String managementServicePort;

}
