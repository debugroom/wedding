package org.debugroom.wedding.domain.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * The persistent class for the address database table.
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
	@Column(name="message_board_id", unique=true, nullable=false, length=8)
	private String messageBoardId;

	@Column(length=512)
	private String title;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="messageBoard")
	private Set<Message> messages;

	public MessageBoard() {
	}

	public String getMessageBoardId() {
		return this.messageBoardId;
	}

	public void setMessageBoardId(String messageBoardId) {
		this.messageBoardId = messageBoardId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messages) {
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