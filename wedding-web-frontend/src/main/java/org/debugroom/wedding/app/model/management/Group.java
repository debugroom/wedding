package org.debugroom.wedding.app.model.management;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Group implements Serializable{
	
	@NotNull(groups = EditUserForm.UpdateUser.class)
	@Size(min=10, max=10, groups=EditUserForm.UpdateUser.class)
	private String groupId;

	@Size(min=0, max=256, groups=EditUserForm.UpdateUser.class)
	private String groupName;


}
