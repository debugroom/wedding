package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the request_status database table.
 * 
 */
@AllArgsConstructor
@Builder
@Embeddable
public class RequestStatusPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="request_id", insertable=false, updatable=false, unique=true, nullable=false, length=4)
	private String requestId;

	@Column(name="user_id", insertable=false, updatable=false, unique=true, nullable=false, length=8)
	private String userId;

	public RequestStatusPK() {
	}
	public String getRequestId() {
		return this.requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
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
		if (!(other instanceof RequestStatusPK)) {
			return false;
		}
		RequestStatusPK castOther = (RequestStatusPK)other;
		return 
			this.requestId.equals(castOther.requestId)
			&& this.userId.equals(castOther.userId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.requestId.hashCode();
		hash = hash * prime + this.userId.hashCode();
		
		return hash;
	}
}
