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

		List<String> frontendServicePathList = new ArrayList<String>();
		pathMap.put(properties.getFrontendServiceName(), frontendServicePathList);
		frontendServicePathList.add(new StringBuilder()
				.append(properties.getFrontendServiceAddr())
				.append(":")
				.append(properties.getFrontendServicePort())
				.toString());

		List<String> portalServicePathList = new ArrayList<String>();
		pathMap.put(properties.getPortalServiceName(), portalServicePathList);
		portalServicePathList.add(new StringBuilder()
				.append(properties.getPortalServiceAddr())
				.append(":")
				.append(properties.getPortalServicePort())
				.toString());

		List<String> informationServicePathList = new ArrayList<String>();
		pathMap.put(properties.getInformationServiceName(), informationServicePathList);
		informationServicePathList.add(new StringBuilder()
				.append(properties.getInformationServiceAddr())
				.append(":")
				.append(properties.getInformationServicePort())
				.toString());
		
		List<String> profileServicePathList = new ArrayList<String>();
		pathMap.put(properties.getProfileServiceName(), profileServicePathList);
		profileServicePathList.add(new StringBuilder()
				.append(properties.getProfileServiceAddr())
				.append(":")
				.append(properties.getProfileServicePort())
				.toString());

		List<String> managementServicePathList = new ArrayList<String>();
		pathMap.put(properties.getManagementServiceName(), managementServicePathList);
		managementServicePathList.add(new StringBuilder()
				.append(properties.getManagementServiceAddr())
				.append(":")
				.append(properties.getManagementServicePort())
				.toString());

		List<String> galleryServicePathList = new ArrayList<String>();
		pathMap.put(properties.getGalleryServiceName(), galleryServicePathList);
		galleryServicePathList.add(new StringBuilder()
				.append(properties.getGalleryServiceAddr())
				.append(":")
				.append(properties.getGalleryServicePort())
				.toString());

		List<String> galleryBatchServicePathList = new ArrayList<String>();
		pathMap.put(properties.getGalleryBatchServiceName(), galleryBatchServicePathList);
		galleryServicePathList.add(new StringBuilder()
				.append(properties.getGalleryBatchServiceAddr())
				.append(":")
				.append(properties.getGalleryBatchServicePort())
				.toString());

		return pathMap;

	}

	@Override
	public Map<String, List<String>> getIpAddrs() {
		Map<String, List<String>> ipAddrMap = new HashMap<String, List<String>>();
		
		List<String> frontendServiceIpAddrList = new ArrayList<String>();
		ipAddrMap.put(properties.getFrontendServiceName(), frontendServiceIpAddrList);
		frontendServiceIpAddrList.add(properties.getFrontendServiceAddr());

		List<String> portalServiceIpAddrList = new ArrayList<String>();
		ipAddrMap.put(properties.getPortalServiceName(), portalServiceIpAddrList);
		portalServiceIpAddrList.add(properties.getPortalServiceAddr());

		List<String> informationServiceIpAddrList = new ArrayList<String>();
		ipAddrMap.put(properties.getInformationServiceName(), informationServiceIpAddrList);
		informationServiceIpAddrList.add(properties.getInformationServiceAddr());

		List<String> profileServiceIpAddrList = new ArrayList<String>();
		ipAddrMap.put(properties.getProfileServiceName(), profileServiceIpAddrList);
		profileServiceIpAddrList.add(properties.getProfileServiceAddr());

		List<String> managementServiceIpAddrList = new ArrayList<String>();
		ipAddrMap.put(properties.getManagementServiceName(), managementServiceIpAddrList);
		managementServiceIpAddrList.add(properties.getManagementServiceAddr());

		List<String> galleryServiceIpAddrList = new ArrayList<String>();
		ipAddrMap.put(properties.getGalleryServiceName(),galleryServiceIpAddrList);
		galleryServiceIpAddrList.add(properties.getGalleryServiceAddr());

		List<String> galleryBatchServiceIpAddrList = new ArrayList<String>();
		ipAddrMap.put(properties.getGalleryBatchServiceName(),galleryBatchServiceIpAddrList);
		galleryBatchServiceIpAddrList.add(properties.getGalleryBatchServiceAddr());

		return ipAddrMap;
	}

	@Override
	public Map<String, List<String>> getPorts() {
		Map<String, List<String>> portMap = new HashMap<String, List<String>>();

		List<String> frontendServicePortList = new ArrayList<String>();
		portMap.put(properties.getFrontendServiceName(), frontendServicePortList);
		frontendServicePortList.add(properties.getFrontendServicePort());

		List<String> portalServicePortList = new ArrayList<String>();
		portMap.put(properties.getPortalServiceName(), portalServicePortList);
		portalServicePortList.add(properties.getPortalServicePort());

		List<String> informationServicePortList = new ArrayList<String>();
		portMap.put(properties.getInformationServiceName(), informationServicePortList);
		informationServicePortList.add(properties.getInformationServicePort());

		List<String> profileServicePortList = new ArrayList<String>();
		portMap.put(properties.getProfileServiceName(), profileServicePortList);
		profileServicePortList.add(properties.getProfileServicePort());

		List<String> managementServicePortList = new ArrayList<String>();
		portMap.put(properties.getManagementServiceName(), managementServicePortList);
		managementServicePortList.add(properties.getManagementServicePort());

		List<String> galleryServicePortList = new ArrayList<String>();
		portMap.put(properties.getGalleryServiceName(), galleryServicePortList);
		galleryServicePortList.add(properties.getGalleryServicePort());

		List<String> galleryBatchServicePortList = new ArrayList<String>();
		portMap.put(properties.getGalleryBatchServiceName(), galleryBatchServicePortList);
		galleryBatchServicePortList.add(properties.getGalleryBatchServicePort());

		return portMap;

	}

}
