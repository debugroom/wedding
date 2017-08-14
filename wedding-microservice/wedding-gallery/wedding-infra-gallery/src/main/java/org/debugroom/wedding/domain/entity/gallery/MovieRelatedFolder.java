package org.debugroom.wedding.domain.entity.gallery;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;


/**
 * The persistent class for the movie_related_folder database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="movie_related_folder")
@NamedQuery(name="MovieRelatedFolder.findAll", query="SELECT m FROM MovieRelatedFolder m")
public class MovieRelatedFolder implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MovieRelatedFolderPK id;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to Folder
	@ManyToOne
	@JoinColumn(name="folder_id", nullable=false, insertable=false, updatable=false)
	private Folder folder;

	//bi-directional many-to-one association to Movie
	@ManyToOne
	@JoinColumn(name="movie_id", nullable=false, insertable=false, updatable=false)
	private Movie movie;

	public MovieRelatedFolder() {
	}

	public MovieRelatedFolderPK getId() {
		return this.id;
	}

	public void setId(MovieRelatedFolderPK id) {
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

	@JsonIgnore
	public Folder getFolder() {
		return this.folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	@JsonIgnore
	public Movie getMovie() {
		return this.movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

}
