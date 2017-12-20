package org.debugroom.wedding.app.web.adapter.docker.provider;

import java.util.List;
import java.util.Map;

public interface ConnectPathHolder {
	
	public Map<String, List<String>> getPaths();

	public Map<String, List<String>> getIpAddrs();
	
	public Map<String, List<String>> getPorts();
	
}
