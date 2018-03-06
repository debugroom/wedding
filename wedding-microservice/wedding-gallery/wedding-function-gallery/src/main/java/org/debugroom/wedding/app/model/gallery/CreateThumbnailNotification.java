package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class CreateThumbnailNotification implements Serializable{

	private static final long serialVersionUID = -9032581314531783202L;

	public CreateThumbnailNotification(){
	}

	private String folderId;
	private String userId;
	private String originalObjectKey;
	private String thumbnailObjectKey;
	
}
