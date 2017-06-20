package org.debugroom.wedding.app.web.adapter.docker;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.debugroom.wedding.app.model.portal.PortalResource;
import org.debugroom.wedding.app.web.adapter.docker.provider.ConnectPathProvider;
import org.debugroom.wedding.app.web.util.RequestBuilder;

@Controller
public class ServiceAdpaterController {

	private static final String APP_NAME = "api/v1";

	@Inject
	ConnectPathProvider provider;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() throws RestClientException, URISyntaxException{
		String serviceName = "login";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getForObject(
				RequestBuilder.getGetRequest(provider.getPath(serviceName), 
						APP_NAME, serviceName, null, null),
				String.class);
		return serviceName;
	}
	
	@RequestMapping(value="/portal/{userId}", method=RequestMethod.GET)
	public String portal(@PathVariable String userId, Model model) 
			throws URISyntaxException{
		String serviceName = "portal";
		Map<Integer, String> pathVariableMap = new HashMap<Integer, String>();
		pathVariableMap.put(0, userId);
		RestTemplate restTemplate = new RestTemplate();
		try {
			PortalResource portalResource = restTemplate.getForObject(
					RequestBuilder.getGetRequest(provider.getPath(serviceName), 
							APP_NAME, serviceName, null, pathVariableMap),
					PortalResource.class);
			model.addAttribute(portalResource);
		} catch (RestClientException e) {
			// TODO Consideration exception handling
			e.printStackTrace();
		}
		return serviceName;
	}
	
}
