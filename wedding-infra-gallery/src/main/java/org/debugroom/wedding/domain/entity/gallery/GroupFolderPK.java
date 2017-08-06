package org.debugroom.wedding.domain.entity.gallery;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the group_folder database table.
 * 
 */
@AllArgsConstructor
@Builder
@Embeddable
public class GroupFolderPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="folder_id", insertable=false, updatable=false)
	private String folderId;

	@Column(name="group_id", insertable=false, updatable=false)
	private String groupId;

	public GroupFolderPK() {
	}
	public String getFolderId() {
		return this.folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	public String getGroupId() {
		return this.groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GroupFolderPK)) {
			return false;
		}
		GroupFolderPK castOther = (GroupFolderPK)other;
		return 
			this.folderId.equals(castOther.folderId)
			&& this.groupId.equals(castOther.groupId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.folderId.hashCode();
		hash = hash * prime + this.groupId.hashCode();
		
		return hash;
	}
}
