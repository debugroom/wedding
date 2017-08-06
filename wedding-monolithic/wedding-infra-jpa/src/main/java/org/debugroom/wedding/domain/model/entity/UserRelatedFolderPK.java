package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the user_related_folder database table.
 * 
 */
@AllArgsConstructor
@Builder
@Embeddable
public class UserRelatedFolderPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="folder_id", insertable=false, updatable=false, unique=true, nullable=false, length=12)
	private String folderId;

	@Column(name="user_id", insertable=false, updatable=false, unique=true, nullable=false, length=8)
	private String userId;

	public UserRelatedFolderPK() {
	}
	public String getFolderId() {
		return this.folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserRelatedFolderPK)) {
			return false;
		}
		UserRelatedFolderPK castOther = (UserRelatedFolderPK)other;
		return 
			this.folderId.equals(castOther.folderId)
			&& this.userId.equals(castOther.userId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.folderId.hashCode();
		hash = hash * prime + this.userId.hashCode();
		
		return hash;
	}
}
