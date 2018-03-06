package org.debugroom.wedding.app.model.aws.gallery;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateThumbnailNotification implements Serializable{
	
	private static final long serialVersionUID = -7105935131716553434L;
	
	public CreateThumbnailNotification(){
	}
	
	public CreateThumbnailNotification(String folderId){
		this.folderId = folderId;
	}
	
	public CreateThumbnailNotification(String folderId, String userId){
		this.folderId = folderId;
		this.userId = userId;
	}

	public CreateThumbnailNotification(String folderId, String userId, String originalObjectKey){
		this.folderId = folderId;
		this.userId = userId;
		this.originalObjectKey = originalObjectKey;
	}
	
	public CreateThumbnailNotification(String folderId, String userId, 
			String originalObjectKey, String thumbnailObjectKey){
		this.folderId = folderId;
		this.userId = userId;
		this.originalObjectKey = originalObjectKey;
		this.thumbnailObjectKey = thumbnailObjectKey;
	}

	/*
	@JsonCreator
	public CreateThumbnailNotification(
			@JsonProperty("folderId") String folderId, 
			@JsonProperty("userId") String userId,
			@JsonProperty("originalObjectKey") String originalObjectKey,
			@JsonProperty("thumbnailObjectKey") String thumbnailObjectKey
			){
		this.folderId = folderId;
		this.userId = userId;
		this.originalObjectKey = originalObjectKey;
		this.thumbnailObjectKey = thumbnailObjectKey;
	}
	 */

	private String folderId;
	private String userId;
	private String originalObjectKey;
	private String thumbnailObjectKey;

}
