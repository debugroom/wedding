package org.debugroom.wedding.app.model.profile;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.validation.Valid;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class Credential implements Serializable{

	private static final long serialVersionUID = -7189769640776860740L;

	public Credential(){
	}
	
	@Valid
	private CredentialPK id;
	
	@Size(min = 0, max = 32)
	@Pattern(regexp = "[¥ -¥.a-zA-Z0-9]*")
	private String credentialKey;

}
