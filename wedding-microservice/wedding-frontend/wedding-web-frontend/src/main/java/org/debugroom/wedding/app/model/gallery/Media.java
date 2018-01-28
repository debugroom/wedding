package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Media implements Serializable{

	private static final long serialVersionUID = -6297850754933136245L;

	public Media(){
	}
	
	@NotNull
	@Size(min=10, max=10)
	@Pattern(regexp = "[0-9]*")
	private String mediaId;
	private MediaType mediaType;
	private String extension;
	private String originalFilename;
	private String filePath;
	private String thumbnailFilePath;
	private String folderId;
	private String userId;
	
	
	
}
