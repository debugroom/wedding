package org.debugroom.wedding.app.web.adapter.docker.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

@Component
public class EnvironmentVariablesHolder implements ConnectPathHolder{

	@Inject
	ServiceProperties properties;
	
	@Override
	public Map<String, List<String>> getPaths() {
		Map<String, List<String>> pathMap = new HashMap<String, List<String>>();
		List<String> portalServicePathList = new ArrayList<String>();
		pathMap.put(properties.getPortalServiceName(), portalServicePathList);
		portalServicePathList.add(new StringBuilder()
				.append(properties.getPortalServiceAddr())
				.append(":")
				.append(properties.getPortalServicePort())
				.toString());
		return pathMap;
	}

}
