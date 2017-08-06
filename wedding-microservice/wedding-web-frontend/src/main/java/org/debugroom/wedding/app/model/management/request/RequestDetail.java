package org.debugroom.wedding.app.model.management.request;

import java.io.Serializable;

import java.util.List;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class RequestDetail implements Serializable{

	private static final long serialVersionUID = -8400362288684141656L;

	public RequestDetail(){
	}
	
	private Request request;
	private List<User> approvedUsers;
	private List<User> deniedUsers;
	private String notRequestUsersUrl;
	
}
