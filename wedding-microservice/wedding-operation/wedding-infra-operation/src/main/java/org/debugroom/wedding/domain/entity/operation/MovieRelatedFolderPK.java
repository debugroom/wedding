package org.debugroom.wedding.domain.entity.operation;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.*;

import org.debugroom.wedding.domain.entity.operation.Folder.FolderBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * The primary key class for the movie_related_folder database table.
 * 
 */
@AllArgsConstructor
@Builder
@Embeddable
public class MovieRelatedFolderPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="movie_id", insertable=false, updatable=false)
	private String movieId;

	@Column(name="folder_id", insertable=false, updatable=false)
	private String folderId;

	public MovieRelatedFolderPK() {
	}
	public String getMovieId() {
		return this.movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getFolderId() {
		return this.folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MovieRelatedFolderPK)) {
			return false;
		}
		MovieRelatedFolderPK castOther = (MovieRelatedFolderPK)other;
		return 
			this.movieId.equals(castOther.movieId)
			&& this.folderId.equals(castOther.folderId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.movieId.hashCode();
		hash = hash * prime + this.folderId.hashCode();
		
		return hash;
	}
}