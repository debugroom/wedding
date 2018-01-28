package org.debugroom.wedding.app.web.management.user;

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

	private static final long serialVersionUID = 1L;
	
	public Email(){
		this.id = new EmailPK();
	}

	@Valid
	private EmailPK id;

	@org.hibernate.validator.constraints.Email(groups = {EditUserForm.UpdateUser.class,
			NewUserForm.ConfirmUser.class, NewUserForm.SaveUser.class})
	@Size(min=0, max=256, groups = {EditUserForm.UpdateUser.class,
			NewUserForm.ConfirmUser.class, NewUserForm.SaveUser.class})
	private String email;

	@AllArgsConstructor
	@Data
	public class EmailPK{

		public EmailPK(){}

		private String userId;

		@Min(value = 0, groups = {EditUserForm.UpdateUser.class,
			NewUserForm.ConfirmUser.class, NewUserForm.SaveUser.class})
		private Integer emailId;
		
	}
}