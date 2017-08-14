package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;

import org.debugroom.wedding.domain.model.gallery.MediaType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Media implements Serializable{

	private static final long serialVersionUID = 7266304242491328493L;

	public Media(){
	}
	
	private String mediaId;
	private MediaType mediaType;
	private String extension;
	private String originalFilename;
	private String filePath;
	private String thumbnailFilePath;
	private String folderId;
	private String userId;
	
}
