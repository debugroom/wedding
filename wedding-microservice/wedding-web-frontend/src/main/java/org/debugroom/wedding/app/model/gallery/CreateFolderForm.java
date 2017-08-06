package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class CreateFolderForm implements Serializable{

	private static final long serialVersionUID = -1653383032231848637L;

	public CreateFolderForm(){
	}
	
	public static interface CreateFolder{}
	
	String userId;
	
	@Valid
	private Folder folder;
	
	@Valid
	private List<User> users;
	
}
