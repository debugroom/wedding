package org.debugroom.wedding.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the message_board database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="message_board")
@NamedQuery(name="MessageBoard.findAll", query="SELECT m FROM MessageBoard m")
public class MessageBoard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="message_board_id")
	private String messageBoardId;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	private String title;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="messageBoard")
	private List<Message> messages;

	public MessageBoard() {
	}

	public String getMessageBoardId() {
		return this.messageBoardId;
	}

	public void setMessageBoardId(String messageBoardId) {
		this.messageBoardId = messageBoardId;
	}

	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getVer() {
		return this.ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public List<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Message addMessage(Message message) {
		getMessages().add(message);
		message.setMessageBoard(this);

		return message;
	}

	public Message removeMessage(Message message) {
		getMessages().remove(message);
		message.setMessageBoard(null);

		return message;
	}

}
