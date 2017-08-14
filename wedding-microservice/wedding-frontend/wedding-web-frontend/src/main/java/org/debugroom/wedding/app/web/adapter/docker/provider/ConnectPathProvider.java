package org.debugroom.wedding.app.web.adapter.docker.provider;

public interface ConnectPathProvider {

	public String getPath(String serviceName);

	public String getIpAddr(String serviceName);

	public String getPort(String serviceName);

}
