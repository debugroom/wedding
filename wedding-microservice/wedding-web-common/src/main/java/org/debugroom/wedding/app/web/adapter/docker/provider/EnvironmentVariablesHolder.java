package org.debugroom.wedding.app.web.adapter.docker.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentVariablesHolder implements ConnectPathHolder{

	private static final String PROPERTY_FILE_NAME = "class path resource [docker-service.properties]";
	private static final String DEV_PROPERTY_FILE_NAME = "class path resource [docker-service-dev.properties]";
	
	@Inject
	Environment environment;

	@Override
	public Map<String, List<String>> getIpAddrs() {
		Map<String, List<String>> ipAddrMap = new HashMap<String, List<String>>();
		
		PropertySources propertySources = ((AbstractEnvironment)environment).getPropertySources();
		for(PropertySource<?> propertySource : propertySources){
			if(propertySource instanceof EnumerablePropertySource){
				if(PROPERTY_FILE_NAME.equals(propertySource.getName()) 
						|| DEV_PROPERTY_FILE_NAME.equals(propertySource.getName())){
					for(String propertyName : ((EnumerablePropertySource<?>)propertySource).getPropertyNames()){
						if("name".equals(StringUtils.substringAfterLast(propertyName, "."))){
							List<String> serviceAddrList = new ArrayList<String>();
							Object addrList = propertySource.getProperty(new StringBuilder()
									.append(StringUtils.substringBeforeLast(propertyName, ".")).append(".addr").toString());
							if(addrList instanceof String){
								for(String portString : StringUtils.split((String)addrList, ",")){
									serviceAddrList.add(StringUtils.deleteWhitespace(portString));
								}
							}else if(addrList instanceof Integer){
								serviceAddrList.add(Integer.toString((Integer)addrList));
							}
							ipAddrMap.put((String)propertySource.getProperty(propertyName), serviceAddrList);
						}
					}
				}
			}
		}

		return ipAddrMap;
		
		
	}

	@Override
	public Map<String, List<String>> getPorts() {

		Map<String, List<String>> portMap = new HashMap<String, List<String>>();

		PropertySources propertySources = ((AbstractEnvironment)environment).getPropertySources();
		for(PropertySource<?> propertySource : propertySources){
			if(propertySource instanceof EnumerablePropertySource){
				if(PROPERTY_FILE_NAME.equals(propertySource.getName()) 
						|| DEV_PROPERTY_FILE_NAME.equals(propertySource.getName())){
					for(String propertyName : ((EnumerablePropertySource<?>)propertySource).getPropertyNames()){
						if("name".equals(StringUtils.substringAfterLast(propertyName, "."))){
							List<String> servicePortList = new ArrayList<String>();
							Object portList = propertySource.getProperty(new StringBuilder()
									.append(StringUtils.substringBeforeLast(propertyName, ".")).append(".port").toString());
							if(portList instanceof String){
								for(String portString : StringUtils.split((String)portList, ",")){
									servicePortList.add(StringUtils.deleteWhitespace(portString));
								}
							}else if(portList instanceof Integer){
								servicePortList.add(Integer.toString((Integer)portList));
							}
							portMap.put((String)propertySource.getProperty(propertyName), servicePortList);
						}
					}
				}
			}
		}
		return portMap;

	}

}
