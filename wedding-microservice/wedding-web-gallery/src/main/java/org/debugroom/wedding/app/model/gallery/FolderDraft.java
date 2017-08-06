package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class FolderDraft implements Serializable{

	private static final long serialVersionUID = 5548856108692900599L;

	public FolderDraft(){
	}
	
	private String userId;
	private Folder folder;
	private List<User> users;
	private List<User> checkedAddUsers;
	private List<User> checkedDeleteUsers;
	
}
