package org.debugroom.wedding.domain.entity.operation;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.*;

import org.debugroom.wedding.domain.entity.operation.Folder.FolderBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * The primary key class for the group_visible_photo database table.
 * 
 */
@AllArgsConstructor
@Builder
@Embeddable
public class GroupVisiblePhotoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="group_id", insertable=false, updatable=false)
	private String groupId;

	@Column(name="photo_id", insertable=false, updatable=false)
	private String photoId;

	public GroupVisiblePhotoPK() {
	}
	public String getGroupId() {
		return this.groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getPhotoId() {
		return this.photoId;
	}
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GroupVisiblePhotoPK)) {
			return false;
		}
		GroupVisiblePhotoPK castOther = (GroupVisiblePhotoPK)other;
		return 
			this.groupId.equals(castOther.groupId)
			&& this.photoId.equals(castOther.photoId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.groupId.hashCode();
		hash = hash * prime + this.photoId.hashCode();
		
		return hash;
	}
}