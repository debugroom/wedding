package org.debugroom.wedding.app.model.management.information;

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

	private static final long serialVersionUID = -4061678239771517451L;

	public UserSearchResult(){
	}

	private String infoId;
	private List<User> users;
	
	
}
