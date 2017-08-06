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
public class CreateFolderForm implements Serializable{

	private static final long serialVersionUID = 1289629562163369342L;

	public CreateFolderForm(){
	}

	public static interface CreateFolder{}

	@Valid
	private Folder folder;

	@Valid
	private List<User> users;

}
