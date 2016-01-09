package org.debugroom.wedding.domain.model.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * The persistent class for the address database table.
 * 
 */
@AllArgsConstructor
@Builder
@Embeddable
public class MessagePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="message_board_id", insertable=false, updatable=false, unique=true, nullable=false, length=8)
	private String messageBoardId;

	@Column(name="message_no", unique=true, nullable=false)
	private Integer messageNo;

	public MessagePK() {
	}
	public String getMessageBoardId() {
		return this.messageBoardId;
	}
	public void setMessageBoardId(String messageBoardId) {
		this.messageBoardId = messageBoardId;
	}
	public Integer getMessageNo() {
		return this.messageNo;
	}
	public void setMessageNo(Integer messageNo) {
		this.messageNo = messageNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MessagePK)) {
			return false;
		}
		MessagePK castOther = (MessagePK)other;
		return 
			this.messageBoardId.equals(castOther.messageBoardId)
			&& this.messageNo.equals(castOther.messageNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.messageBoardId.hashCode();
		hash = hash * prime + this.messageNo.hashCode();
		
		return hash;
	}
}