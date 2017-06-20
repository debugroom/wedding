package org.debugroom.wedding.app.web.adapter.docker.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ServiceProperties {

	@Value("${portal.service.name}")
	private String portalServiceName;
	
	@Value("${portal.service.addr}")
	private String portalServiceAddr;

	@Value("${portal.service.port}")
	private String portalServicePort;

}
