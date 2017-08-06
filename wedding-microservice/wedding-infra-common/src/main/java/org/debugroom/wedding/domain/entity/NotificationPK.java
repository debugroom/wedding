package org.debugroom.wedding.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the notification database table.
 * 
 */
@AllArgsConstructor
@Builder
@Embeddable
public class NotificationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="info_id", insertable=false, updatable=false)
	private String infoId;

	@Column(name="user_id", insertable=false, updatable=false)
	private String userId;

	public NotificationPK() {
	}
	public String getInfoId() {
		return this.infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
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
		if (!(other instanceof NotificationPK)) {
			return false;
		}
		NotificationPK castOther = (NotificationPK)other;
		return 
			this.infoId.equals(castOther.infoId)
			&& this.userId.equals(castOther.userId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.infoId.hashCode();
		hash = hash * prime + this.userId.hashCode();
		
		return hash;
	}
}
