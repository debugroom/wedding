package org.debugroom.wedding.app.web.common.model;

import java.util.List;

import org.debugroom.wedding.domain.model.entity.User;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Builder
@Data
public class UserSearchResult {

	public UserSearchResult(){
	}
	
	private String infoId;
	private String requestId;
	private List<User> users;
	private List<String> messages;

}
