package org.debugroom.wedding.app.model.profile;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import javax.validation.Valid;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class Email implements Serializable{

	private static final long serialVersionUID = -469024683298608423L;

	public Email(){
		this.id = new EmailPK();
	}

	@Valid
	private EmailPK id;
	
	@NotNull
	@org.hibernate.validator.constraints.Email
	@Size(min=0, max=256)
	private String email;

}
