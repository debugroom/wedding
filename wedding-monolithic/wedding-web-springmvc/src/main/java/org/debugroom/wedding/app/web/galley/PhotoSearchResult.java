package org.debugroom.wedding.app.web.galley;

import java.util.List;

import org.debugroom.wedding.domain.model.entity.Photo;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Builder
@Data
public class PhotoSearchResult {

	public PhotoSearchResult(){
	}
	
	private String folderId;
	private String folderName;
	private String requestContextPath;
	private List<Photo> photographs;
	private List<String> messages;

}
