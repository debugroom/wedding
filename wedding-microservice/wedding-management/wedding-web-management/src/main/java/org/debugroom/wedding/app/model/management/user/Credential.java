package org.debugroom.wedding.app.model.management.user;

import java.io.Serializable;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class Credential implements Serializable{

	private static final long serialVersionUID = -3597301074005102229L;

	public Credential(){
	}

	private CredentialPK id;
	private String credentialKey;
	
}

