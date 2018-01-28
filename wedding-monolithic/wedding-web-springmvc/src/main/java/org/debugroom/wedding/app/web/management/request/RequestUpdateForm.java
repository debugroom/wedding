package org.debugroom.wedding.app.web.management.request;

import java.io.Serializable;

import java.util.List;

import javax.validation.Valid;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class RequestUpdateForm implements Serializable{

	private static final long serialVersionUID = 79824661335042106L;

	public RequestUpdateForm(){
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
	
}
