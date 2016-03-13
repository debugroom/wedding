package org.debugroom.wedding.app.web.management.user;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Group implements Serializable{

	private static final long serialVersionUID = 880436949905342202L;

	@NotNull(groups = EditUserForm.UpdateUser.class)
	@Size(min=10, max=10, groups=EditUserForm.UpdateUser.class)
	private String groupId;

	@Size(min=0, max=256, groups=EditUserForm.UpdateUser.class)
	private String groupName;

}
