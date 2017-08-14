package org.debugroom.wedding.app.model.profile;

import java.io.Serializable;

import javax.validation.constraints.Size;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class CredentialPK implements Serializable{

	private static final long serialVersionUID = 5567528097296595418L;

	public CredentialPK(){
	}
	@Size(min = 8, max = 8)
	private String userId;
	private String credentialType;

}
