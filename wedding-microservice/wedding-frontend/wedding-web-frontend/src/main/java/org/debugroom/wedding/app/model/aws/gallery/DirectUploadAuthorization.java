package org.debugroom.wedding.app.model.aws.gallery;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DirectUploadAuthorization implements Serializable{

	private static final long serialVersionUID = 5463414538313406999L;

	public DirectUploadAuthorization(){
	}

	private String objectKey;
	private String acl;
	private String uploadUrl;
	private String policy;
	private String securityToken;
	private String date;
	private String algorithm;
	private String credential;
	private String signature;
	private String folderId;
	private String userId;
	private String fileSizeLimit;
	
}
