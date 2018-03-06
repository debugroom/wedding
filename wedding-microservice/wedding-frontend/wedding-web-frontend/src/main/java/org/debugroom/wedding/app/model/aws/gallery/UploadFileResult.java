package org.debugroom.wedding.app.model.aws.gallery;

import java.io.Serializable;
import java.util.List;

import org.debugroom.wedding.app.model.aws.gallery.Media;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UploadFileResult implements Serializable{
	
	private static final long serialVersionUID = -4303474150017901591L;
	
	public UploadFileResult(){
	}

	private Media media;
	private String thumbnailPresignedUrl;
	private List<String> messages;

}
