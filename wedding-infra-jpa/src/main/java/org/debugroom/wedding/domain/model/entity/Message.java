package org.debugroom.wedding.domain.model.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the message database table.
 * 
 */
@Entity
@NamedQuery(name="Message.findAll", query="SELECT m FROM Message m")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="message_no")
	private Integer messageNo;

	private String message;

	//bi-directional many-to-one association to MessageBoard
	@ManyToOne
	@JoinColumn(name="message_board_id")
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

	public Integer getMessageNo() {
		return this.messageNo;
	}

	public void setMessageNo(Integer messageNo) {
		this.messageNo = messageNo;
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