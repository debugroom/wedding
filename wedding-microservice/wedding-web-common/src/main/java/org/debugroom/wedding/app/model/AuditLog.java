package org.debugroom.wedding.app.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuditLog implements Serializable{

	private static final long serialVersionUID = -5814736439601937647L;

	public AuditLog(){
	}
	private LogPK logPK;
	private String serviceName;
	private String hostIpAddress;
	private String trackId;
	private String sessionId;
	private String requestIpAddress;
	private String referer;
	private String userAgent;
	private String cookie;
	private String headerDump;
	private String viewName;
	private String parameters;

}
