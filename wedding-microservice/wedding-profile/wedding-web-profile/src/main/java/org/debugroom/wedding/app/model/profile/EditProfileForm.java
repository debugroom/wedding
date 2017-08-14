package org.debugroom.wedding.app.model.profile;

import java.io.Serializable;

import javax.validation.Valid;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class EditProfileForm implements Serializable{

	private static final long serialVersionUID = -40437133327746874L;

	public EditProfileForm(){
	}

	@Valid
	private User user;

}
