package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the email database table.
 * 
 */
@AllArgsConstructor
@Builder
@Embeddable
public class EmailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id", insertable=false, updatable=false, unique=true, nullable=false, length=8)
	private String userId;

	@Column(name="email_id", unique=true, nullable=false)
	private Integer emailId;

	public EmailPK() {
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getEmailId() {
		return this.emailId;
	}
	public void setEmailId(Integer emailId) {
		this.emailId = emailId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EmailPK)) {
			return false;
		}
		EmailPK castOther = (EmailPK)other;
		return 
			this.userId.equals(castOther.userId)
			&& this.emailId.equals(castOther.emailId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.emailId.hashCode();
		
		return hash;
	}
}
