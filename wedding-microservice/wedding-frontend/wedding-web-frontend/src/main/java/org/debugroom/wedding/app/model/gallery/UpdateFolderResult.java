package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@AllArgsConstructor
@Builder
@Data
public class UpdateFolderResult implements Serializable{

	private static final long serialVersionUID = 2114746901822217531L;

	public UpdateFolderResult(){
	}
	
	private Folder folder;
	private String requestContextPath;
	List<String> messages;

}
