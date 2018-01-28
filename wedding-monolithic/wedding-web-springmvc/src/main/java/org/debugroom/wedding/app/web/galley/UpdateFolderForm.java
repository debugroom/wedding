package org.debugroom.wedding.app.web.galley;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UpdateFolderForm implements Serializable{

	private static final long serialVersionUID = 5833372274743022733L;

	public UpdateFolderForm(){
	}
	
	public static interface UpdateFolder{}
	
	@Valid
	private Folder folder;
	
	@Valid
	private List<User> checkedAddUsers;
	
	@Valid
	private List<User> checkedDeleteUsers;

	
}
