package org.debugroom.wedding.domain.entity.gallery;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the photo_related_folder database table.
 * 
 */
@AllArgsConstructor
@Builder
@Embeddable
public class PhotoRelatedFolderPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="photo_id", insertable=false, updatable=false)
	private String photoId;

	@Column(name="folder_id", insertable=false, updatable=false)
	private String folderId;

	public PhotoRelatedFolderPK() {
	}
	public String getPhotoId() {
		return this.photoId;
	}
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	public String getFolderId() {
		return this.folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PhotoRelatedFolderPK)) {
			return false;
		}
		PhotoRelatedFolderPK castOther = (PhotoRelatedFolderPK)other;
		return 
			this.photoId.equals(castOther.photoId)
			&& this.folderId.equals(castOther.folderId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.photoId.hashCode();
		hash = hash * prime + this.folderId.hashCode();
		
		return hash;
	}
}
