package org.debugroom.wedding.app.web.management.user;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class Address implements Serializable{

	private static final long serialVersionUID = 1L;

	public Address(){}

	@NotNull(groups = EditUserForm.UpdateUser.class)
	@Size(min = 8, max=8, groups = EditUserForm.UpdateUser.class)
	@Pattern(regexp = "[-¥ 0-9]*", groups = EditUserForm.UpdateUser.class)
	private String postCd;
	@NotNull(groups = EditUserForm.UpdateUser.class)
	@Size(min = 0, max=256, groups = EditUserForm.UpdateUser.class)
	private String address;

}