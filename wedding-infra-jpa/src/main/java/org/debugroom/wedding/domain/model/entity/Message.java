package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the message database table.
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

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Column(name="like_count")
	private Integer likeCount;

	@Column(length=2147483647)
	private String message;

	@Version
	private Integer ver;

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

	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Integer getLikeCount() {
		return this.likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getVer() {
		return this.ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
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
