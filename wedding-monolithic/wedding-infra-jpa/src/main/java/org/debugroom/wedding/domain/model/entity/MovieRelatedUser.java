package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the movie_related_user database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="movie_related_user")
@NamedQuery(name="MovieRelatedUser.findAll", query="SELECT m FROM MovieRelatedUser m")
public class MovieRelatedUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MovieRelatedUserPK id;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to Movie
	@ManyToOne
	@JoinColumn(name="movie_id", nullable=false, insertable=false, updatable=false)
	private Movie movie;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false, insertable=false, updatable=false)
	private User usr;

	public MovieRelatedUser() {
	}

	public MovieRelatedUserPK getId() {
		return this.id;
	}

	public void setId(MovieRelatedUserPK id) {
		this.id = id;
	}

	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Integer getVer() {
		return this.ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public Movie getMovie() {
		return this.movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public User getUsr() {
		return this.usr;
	}

	public void setUsr(User usr) {
		this.usr = usr;
	}

}
