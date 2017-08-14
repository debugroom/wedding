package org.debugroom.wedding.app.model.management.information;

import java.io.Serializable;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.debugroom.wedding.app.model.management.information.Information.UpdateInformation;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class UpdateInformationForm implements Serializable{
	
	private static final long serialVersionUID = 189707301335406609L;

	public UpdateInformationForm(){
	}

	@Valid
	private Information information;

	@Valid
	private List<User> accessedUsers;
	
	@Valid
	private List<User> noAccessedUsers;
	
	@Valid
	private List<User> checkedAddUsers;
	
	@Valid
	private List<User> checkedDeleteUsers;
	
	@NotNull(groups = {UpdateInformation.class})
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-]*", groups={UpdateInformation.class})
	private String type;
	
}

