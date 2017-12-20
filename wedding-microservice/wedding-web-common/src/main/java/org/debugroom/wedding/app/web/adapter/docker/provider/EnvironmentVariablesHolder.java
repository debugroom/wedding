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
		galleryBatchServicePathList.add(new StringBuilder()
				.append(properties.getGalleryBatchServiceAddr())
				.append(":")
				.append(properties.getGalleryBatchServicePort())
				.toString());

		List<String> galleryDistributionServicePathList = new ArrayList<String>();
		pathMap.put(properties.getGalleryDistributionServiceName(), galleryDistributionServicePathList);
		galleryDistributionServicePathList.add(new StringBuilder()
				.append(properties.getGalleryDistributionServiceAddr())
				.append(":")
				.append(properties.getGalleryDistributionServicePort())
				.toString());

		List<String> messageServicePathList = new ArrayList<String>();
		pathMap.put(properties.getMessageServiceName(), messageServicePathList);
		messageServicePathList.add(new StringBuilder()
				.append(properties.getMessageServiceAddr())
				.append(":")
				.append(properties.getMessageServicePort())
				.toString());

		List<String> loginServicePathList = new ArrayList<String>();
		pathMap.put(properties.getLoginServiceName(),loginServicePathList);
		loginServicePathList.add(new StringBuilder()
				.append(properties.getLoginServiceAddr())
				.append(":")
				.append(properties.getLoginServicePort())
				.toString());

		List<String> operationServicePathList = new ArrayList<String>();
		pathMap.put(properties.getOperationServiceName(), operationServicePathList);
		operationServicePathList.add(new StringBuilder()
				.append(properties.getOperationServiceAddr())
				.append(":")
				.append(properties.getOperationServicePort())
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

		List<String> galleryDistributionServiceIpAddrList = new ArrayList<String>();
		ipAddrMap.put(properties.getGalleryDistributionServiceName(),galleryDistributionServiceIpAddrList);
		galleryDistributionServiceIpAddrList.add(properties.getGalleryDistributionServiceAddr());

		List<String> messageServiceIpAddrList = new ArrayList<String>();
		ipAddrMap.put(properties.getMessageServiceName(), messageServiceIpAddrList);
		messageServiceIpAddrList.add(properties.getMessageServiceAddr());

		List<String> loginServiceIpAddrList = new ArrayList<String>();
		ipAddrMap.put(properties.getLoginServiceName(), loginServiceIpAddrList);
		loginServiceIpAddrList.add(properties.getLoginServiceAddr());

		List<String> operationServiceIpAddrList = new ArrayList<String>();
		ipAddrMap.put(properties.getOperationServiceName(), operationServiceIpAddrList);
		operationServiceIpAddrList.add(properties.getOperationServiceAddr());

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

		List<String> galleryDistributionServicePortList = new ArrayList<String>();
		portMap.put(properties.getGalleryDistributionServiceName(), galleryDistributionServicePortList);
		galleryDistributionServicePortList.add(properties.getGalleryDistributionServicePort());

		List<String> messageServicePortList = new ArrayList<String>();
		portMap.put(properties.getMessageServiceName(), messageServicePortList);
		messageServicePortList.add(properties.getMessageServicePort());

		List<String> loginServicePortList = new ArrayList<String>();
		portMap.put(properties.getLoginServiceName(), loginServicePortList);
		loginServicePortList.add(properties.getLoginServicePort());

		List<String> operationServicePortList = new ArrayList<String>();
		portMap.put(properties.getOperationServiceName(), operationServicePortList);
		operationServicePortList.add(properties.getOperationServicePort());

		return portMap;

	}

}
