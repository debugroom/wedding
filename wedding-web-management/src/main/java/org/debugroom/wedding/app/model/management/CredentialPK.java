package org.debugroom.wedding.app.model.management;

import java.io.Serializable;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class CredentialPK implements Serializable{

	private static final long serialVersionUID = 2846731300783144784L;

	public CredentialPK(){
	}
	
	private String userId;
	private String credentialType;
	
}
