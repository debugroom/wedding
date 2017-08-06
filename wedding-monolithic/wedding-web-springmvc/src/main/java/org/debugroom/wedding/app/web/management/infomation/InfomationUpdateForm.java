package org.debugroom.wedding.app.web.management.infomation;

import java.io.Serializable;

import java.util.List;

import javax.validation.groups.Default;
import javax.validation.Valid;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class InfomationUpdateForm implements Serializable{

	private static final long serialVersionUID = -5586454757889016753L;
	
	public InfomationUpdateForm(){
	}

	@Valid
	private Infomation infomation;
	
	@Valid
	private List<User> accessedUsers;
	
	@Valid
	private List<User> noAccessedUsers;

	@Valid
	private List<User> checkedAddUsers;
	
	@Valid
	private List<User> checkedDeleteUsers;
	
}
