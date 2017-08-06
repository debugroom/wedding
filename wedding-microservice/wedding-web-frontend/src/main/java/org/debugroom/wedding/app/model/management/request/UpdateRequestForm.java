package org.debugroom.wedding.app.model.management.request;

import java.io.Serializable;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class UpdateRequestForm implements Serializable{

	private static final long serialVersionUID = -2777798123022298069L;

	public static interface UpdateRequest{};
	
	public UpdateRequestForm(){
	}

	@Valid
	private Request request;
	
	@Valid
	private List<User> approvedUsers;
	
	@Valid
	private List<User> deniedUsers;
	
	@Valid
	private List<User> checkedAddUsers;
	
	@Valid
	private List<User> checkedDeleteUsers;
	
	@NotNull
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-]*")
	private String type;

}
