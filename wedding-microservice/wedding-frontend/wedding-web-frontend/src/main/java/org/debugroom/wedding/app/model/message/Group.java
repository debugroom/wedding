package org.debugroom.wedding.app.model.message;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Group implements Serializable{

	private static final long serialVersionUID = 2841542586211232592L;

	public Group(){
	}
	
	private Long groupId;
	private String groupName;
	private List<User> users;
	
}
