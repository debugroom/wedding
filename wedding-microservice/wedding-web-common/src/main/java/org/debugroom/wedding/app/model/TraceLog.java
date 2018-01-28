package org.debugroom.wedding.app.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TraceLog implements Serializable{

	private static final long serialVersionUID = 8441715598833994969L;
	
	public TraceLog(){
	}

	private LogPK logPK;
	private String serviceName;
	private String hostIpAddress;
	private String threadId;
	private String trackId;
	private String componentName;
	private String contentType;
	private String accept;
	private String acceptLanguage;
	private String acceptEncoding;
	private String parameters;

}
