package org.debugroom.wedding.domain.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the message_board database table.
 * 
 */
@Entity
@Table(name="message_board")
@NamedQuery(name="MessageBoard.findAll", query="SELECT m FROM MessageBoard m")
public class MessageBoard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="message_board_id")
	private String messageBoardId;

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