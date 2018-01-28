package org.debugroom.wedding.app.web.helper.docker;


import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.debugroom.wedding.app.model.AuditLog;
import org.debugroom.wedding.app.web.adapter.docker.provider.ConnectPathProvider;
import org.debugroom.wedding.app.web.helper.AuditLoggingHelper;
import org.debugroom.wedding.app.web.util.RequestBuilder;

@Component("auditLoggingHelper")
public class AuditLoggingHelperImpl implements AuditLoggingHelper{

	private static final String APP_NAME = "api/v1";

	@Inject
	ConnectPathProvider provider;
	
	@Override
	public void saveAuditLog(AuditLog auditLog) {
		String serviceName = "operation";
		try{
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.postForObject(RequestBuilder.buildUriComponents(
				serviceName, new StringBuilder()
				.append(APP_NAME)
				.append("/log/audit/new")
				.toString(), provider).toUri(), auditLog, Void.class);
		} catch (RestClientException e){
		}

	}


}
