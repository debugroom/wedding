package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the group_notification database table.
 * 
 */
@AllArgsConstructor
@Builder
@Embeddable
public class GroupNotificationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="group_id", insertable=false, updatable=false, unique=true, nullable=false, length=10)
	private String groupId;

	@Column(name="info_id", insertable=false, updatable=false, unique=true, nullable=false, length=8)
	private String infoId;

	public GroupNotificationPK() {
	}
	public String getGroupId() {
		return this.groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getInfoId() {
		return this.infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GroupNotificationPK)) {
			return false;
		}
		GroupNotificationPK castOther = (GroupNotificationPK)other;
		return 
			this.groupId.equals(castOther.groupId)
			&& this.infoId.equals(castOther.infoId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.groupId.hashCode();
		hash = hash * prime + this.infoId.hashCode();
		
		return hash;
	}
}
