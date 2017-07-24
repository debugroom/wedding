package org.debugroom.wedding.app.model;

import java.io.Serializable;
import java.util.List;

import org.debugroom.wedding.domain.entity.User;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Builder
@Data
public class UserSearchResult implements Serializable{

	private static final long serialVersionUID = -6239258206784567550L;

	public UserSearchResult(){
	}
	
	private String infoId;
	private String requestId;
	private String folderId;
	private List<User> users;
	private List<String> messages;

}
