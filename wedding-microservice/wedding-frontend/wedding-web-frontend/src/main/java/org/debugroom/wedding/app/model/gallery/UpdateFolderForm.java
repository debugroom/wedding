package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.debugroom.wedding.app.model.gallery.CreateFolderForm.CreateFolderFormBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UpdateFolderForm implements Serializable{

	private static final long serialVersionUID = -4140487680621023558L;

	public UpdateFolderForm(){
	}

	public static interface UpdateFolder{}
	
	String userId;
	
	@Valid
	private Folder folder;
	
	@Valid
	private List<User> checkedAddUsers;
	
	@Valid
	private List<User> checkedDeleteUsers;
	
}
