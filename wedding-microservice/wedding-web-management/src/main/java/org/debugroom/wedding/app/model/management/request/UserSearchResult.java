package org.debugroom.wedding.app.model.management.request;

import java.io.Serializable;
import java.util.List;

import org.debugroom.wedding.domain.entity.management.User;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Builder
@Data
public class UserSearchResult implements Serializable{

	private static final long serialVersionUID = 4373859826578475191L;

	public UserSearchResult(){
	}

	private String requestId;
	private List<User> users;
	
}
