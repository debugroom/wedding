package org.debugroom.wedding.app.model.management.request;

import java.util.List;

import org.debugroom.wedding.domain.entity.management.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class RequestFormResource {

	public RequestFormResource(){
	}
	
	private List<User> users;
	
}
