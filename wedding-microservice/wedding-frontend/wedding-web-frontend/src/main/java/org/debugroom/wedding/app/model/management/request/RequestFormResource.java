package org.debugroom.wedding.app.model.management.request;

import java.io.Serializable;
import java.util.List;

import org.debugroom.wedding.domain.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class RequestFormResource implements Serializable{

	private static final long serialVersionUID = 5721612367223750720L;

	public RequestFormResource(){
	}
	
	private List<User> users;
	
}
