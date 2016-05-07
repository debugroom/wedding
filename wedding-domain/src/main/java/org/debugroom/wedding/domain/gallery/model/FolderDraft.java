package org.debugroom.wedding.domain.gallery.model;

import java.io.Serializable;
import java.util.List;

import org.debugroom.wedding.domain.model.entity.Folder;
import org.debugroom.wedding.domain.model.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class FolderDraft implements Serializable{

	private static final long serialVersionUID = 4560585985923159545L;

	public FolderDraft(){
	}
		
	private String userId;
	
	private Folder folder;
	
	private List<User> users;
	
}
