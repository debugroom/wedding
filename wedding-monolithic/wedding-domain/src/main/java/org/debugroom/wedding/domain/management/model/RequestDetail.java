package org.debugroom.wedding.domain.management.model;

import java.io.Serializable;

import java.util.List;

import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.model.entity.Request;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class RequestDetail implements Serializable{

	private static final long serialVersionUID = -5732651375126448426L;

	public RequestDetail(){
	}
	
	private Request request;
	private List<User> approvedUsers;
	private List<User> deniedUsers;
}
