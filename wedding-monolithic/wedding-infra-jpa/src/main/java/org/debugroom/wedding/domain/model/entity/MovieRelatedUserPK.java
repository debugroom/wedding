package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the movie_related_user database table.
 * 
 */
@AllArgsConstructor
@Builder
@Embeddable
public class MovieRelatedUserPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="movie_id", insertable=false, updatable=false, unique=true, nullable=false, length=10)
	private String movieId;

	@Column(name="user_id", insertable=false, updatable=false, unique=true, nullable=false, length=8)
	private String userId;

	public MovieRelatedUserPK() {
	}
	public String getMovieId() {
		return this.movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
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
		if (!(other instanceof MovieRelatedUserPK)) {
			return false;
		}
		MovieRelatedUserPK castOther = (MovieRelatedUserPK)other;
		return 
			this.movieId.equals(castOther.movieId)
			&& this.userId.equals(castOther.userId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.movieId.hashCode();
		hash = hash * prime + this.userId.hashCode();
		
		return hash;
	}
}
