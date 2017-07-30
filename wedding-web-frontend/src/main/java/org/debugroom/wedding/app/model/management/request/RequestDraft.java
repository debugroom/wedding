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

	private static final long serialVersionUID = 5487234377371667019L;

	public RequestDraft(){
	}

	private Request request;
	private List<User> acceptors;
	private List<User> excludeUsers;

}
