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
@Entity
@Table(name="message")
@NamedQuery(name="Message.findAll", query="SELECT m FROM Message m")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MessagePK id;

	@Column(length=2147483647)
	private String message;

	//bi-directional many-to-one association to MessageBoard
	@ManyToOne
	@JoinColumn(name="message_board_id", nullable=false, insertable=false, updatable=false)
	private MessageBoard messageBoard;

	//bi-directional many-to-one association to Movie
	@ManyToOne
	@JoinColumn(name="related_movie_id")
	private Movie movie;

	//bi-directional many-to-one association to Photo
	@ManyToOne
	@JoinColumn(name="related_photo_id")
	private Photo photo;

	public Message() {
	}

	public MessagePK getId() {
		return this.id;
	}

	public void setId(MessagePK id) {
		this.id = id;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageBoard getMessageBoard() {
		return this.messageBoard;
	}

	public void setMessageBoard(MessageBoard messageBoard) {
		this.messageBoard = messageBoard;
	}

	public Movie getMovie() {
		return this.movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Photo getPhoto() {
		return this.photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

}