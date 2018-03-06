package org.debugroom.wedding.app.model.aws.gallery;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.debugroom.wedding.app.model.gallery.MediaType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Media implements Serializable{

	private static final long serialVersionUID = 7514657862700635756L;

	public Media(){
	}

	@NotNull
	@Size(min=10, max=10)
	@Pattern(regexp = "[0-9]*")
	private String mediaId;
	private MediaType mediaType;
	private String requestContextPath;
	private String originalFilename;
	private String filePath;
	private String thumbnailFilePath;
	private String folderId;
	private String userId;

}
