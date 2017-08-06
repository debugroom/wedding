package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class CreateFolderResult implements Serializable{

	private static final long serialVersionUID = 6769297397390356343L;

	public CreateFolderResult(){
	}
	
	private Folder folder;
	private String requestContextPath;
	private List<String> messages;
	
}
