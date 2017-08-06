package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DeleteFolderResult implements Serializable{

	private static final long serialVersionUID = 7732570782233078657L;

	public DeleteFolderResult(){
	}
	
	Folder folder;
	List<String> messages;
	
}
