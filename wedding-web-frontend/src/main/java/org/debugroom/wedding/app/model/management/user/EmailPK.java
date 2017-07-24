package org.debugroom.wedding.app.model.management.user;

import java.io.Serializable;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class EmailPK implements Serializable{

	private static final long serialVersionUID = 673349724368952264L;

	public EmailPK(){
	}

	private String userId;

	@Min(value = 0, groups = {EditUserForm.UpdateUser.class,
		NewUserForm.ConfirmUser.class, NewUserForm.SaveUser.class})
	private Integer emailId;

}
