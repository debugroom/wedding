package org.debugroom.wedding.app.web.adapter.docker.provider;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

@Component
public class RandomAccessProvider implements ConnectPathProvider{

	@Inject
	ConnectPathHolder connectPathHolder;
	
	@Override
	public String getIpAddr(String serviceName) {
		List<String> ipAddrs = connectPathHolder.getIpAddrs().get(serviceName);
		String ipAddr = null;
		if(1 == ipAddrs.size()){
			ipAddr = ipAddrs.get(0);
		}else if(0 != ipAddrs.size()){
			Random random = new Random();
			ipAddr = ipAddrs.get(random.nextInt(ipAddrs.size()));
		}
		return ipAddr;
	}

	@Override
	public String getPort(String serviceName) {
		List<String> ports = connectPathHolder.getPorts().get(serviceName);
		return ports.get(0);
	}
	
}
