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
public class RequestDraft implements Serializable{

	public RequestDraft(){
	}
	
	private Request request;
	private List<User> acceptors;
	private List<User> excludeUsers;
	
}
