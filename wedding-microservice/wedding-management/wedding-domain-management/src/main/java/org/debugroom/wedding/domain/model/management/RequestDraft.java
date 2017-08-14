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
public class RequestDraft implements Serializable{

	private static final long serialVersionUID = -8503917988542668021L;

	public RequestDraft(){
	}

	private Request request;
	private List<User> acceptors;
	private List<User> excludeUsers;

}
