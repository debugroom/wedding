package org.debugroom.wedding.app.web.adapter.docker.aws;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AwsResource implements Serializable{

	private static final long serialVersionUID = 4671313524575577035L;

	public AwsResource(){
	}

	private String distributionServerUrl;
	private String galleryRootPath;
	
}
