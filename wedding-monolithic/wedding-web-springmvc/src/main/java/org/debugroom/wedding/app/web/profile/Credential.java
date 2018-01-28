package org.debugroom.wedding.app.web.profile;

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

	private static final long serialVersionUID = 1L;

	public Credential(){
		this.id = new CredentialPK();
	}

	private CredentialPK id;
	@Size(min = 0, max = 32)
	@Pattern(regexp = "[¥ -¥.a-zA-Z0-9]*")
	private String credentialKey;
	
	@AllArgsConstructor
	@Data
	public class CredentialPK{
		public CredentialPK(){}
		@Size(min = 8, max = 8)
		private String userId;
		private String credentialType;
	}
}
