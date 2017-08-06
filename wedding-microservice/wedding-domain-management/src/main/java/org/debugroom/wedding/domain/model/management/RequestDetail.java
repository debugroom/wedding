package org.debugroom.wedding.domain.model.management;

import java.io.Serializable;

import java.util.List;

import org.debugroom.wedding.domain.entity.management.Request;
import org.debugroom.wedding.domain.entity.management.User;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class RequestDetail implements Serializable{

	private static final long serialVersionUID = 7355864937509479166L;

	public RequestDetail(){
	}

	private Request request;
	private List<User> approvedUsers;
	private List<User> deniedUsers;

		
}
