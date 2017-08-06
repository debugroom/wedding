package org.debugroom.wedding.app.model.management.request;


import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class RequestDraft implements Serializable{

	private static final long serialVersionUID = 7938646636174667535L;

	public RequestDraft(){
	}
	
	private Request request;
	private List<User> checkedAddUsers;
	private List<User> checkedDeleteUsers;

}
