package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Builder
@Data
public class PhotoSearchResult implements Serializable{

	private static final long serialVersionUID = -799318019045597600L;

	public PhotoSearchResult(){
	}

	private String folderId;
	private String folderName;
	private String requestContextPath;
	private List<Photo> photographs;
	private List<String> messages;
	
}
