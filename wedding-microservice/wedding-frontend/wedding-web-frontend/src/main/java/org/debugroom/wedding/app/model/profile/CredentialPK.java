package org.debugroom.wedding.app.model.profile;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
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
	@Size(min=8, max=8)
	@Pattern(regexp = "[0-9]*")
	private String userId;
	@Size(min=0, max=32)
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-]*")
	private String credentialType;

}
