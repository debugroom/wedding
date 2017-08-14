package org.debugroom.wedding.domain.model.gallery;

import java.io.Serializable;
import java.util.List;

import org.debugroom.wedding.domain.entity.gallery.Folder;
import org.debugroom.wedding.domain.entity.gallery.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class FolderDraft implements Serializable{

	private static final long serialVersionUID = -4446374652128890910L;

	public FolderDraft(){
	}
	
	private String userId;
	private Folder folder;
	private List<User> users;
	private List<User> checkedAddUsers;
	private List<User> checkedDeleteUsers;

}
