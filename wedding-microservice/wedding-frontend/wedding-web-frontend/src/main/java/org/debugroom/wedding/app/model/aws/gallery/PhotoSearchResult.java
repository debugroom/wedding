package org.debugroom.wedding.app.model.aws.gallery;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class PhotoSearchResult implements Serializable{

	private static final long serialVersionUID = -6788810566703871787L;

	public PhotoSearchResult(){
	}
	
	private String folderId;
	private String folderName;
	private String requestContextPath;
	private List<Photo> photographs;
	private List<String> messages;

}
