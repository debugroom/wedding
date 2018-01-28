package org.debugroom.wedding.domain.entity.operation;

import java.io.Serializable;
import javax.persistence.*;

import org.debugroom.wedding.domain.entity.operation.Folder.FolderBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the movie database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@NamedQuery(name="org.debugroom.wedding.domain.entity.operation.Movie.findAll", 
	query="SELECT m FROM Movie m")
public class Movie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="movie_id")
	private String movieId;

	@Column(name="file_path")
	private String filePath;

	@Column(name="is_controled")
	private Boolean isControled;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Column(name="thumbnail_file_path")
	private String thumbnailFilePath;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to GroupVisibleMovie
	@OneToMany(mappedBy="movie")
	private Set<GroupVisibleMovie> groupVisibleMovies;

	//bi-directional many-to-one association to MovieRelatedFolder
	@OneToMany(mappedBy="movie")
	private Set<MovieRelatedFolder> movieRelatedFolders;

	//bi-directional many-to-one association to MovieRelatedUser
	@OneToMany(mappedBy="movie")
	private Set<MovieRelatedUser> movieRelatedUsers;

	public Movie() {
	}

	public String getMovieId() {
		return this.movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Boolean getIsControled() {
		return this.isControled;
	}

	public void setIsControled(Boolean isControled) {
		this.isControled = isControled;
	}

	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getThumbnailFilePath() {
		return this.thumbnailFilePath;
	}

	public void setThumbnailFilePath(String thumbnailFilePath) {
		this.thumbnailFilePath = thumbnailFilePath;
	}

	public Integer getVer() {
		return this.ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public Set<GroupVisibleMovie> getGroupVisibleMovies() {
		return this.groupVisibleMovies;
	}

	public void setGroupVisibleMovies(Set<GroupVisibleMovie> groupVisibleMovies) {
		this.groupVisibleMovies = groupVisibleMovies;
	}

	public GroupVisibleMovie addGroupVisibleMovy(GroupVisibleMovie groupVisibleMovy) {
		getGroupVisibleMovies().add(groupVisibleMovy);
		groupVisibleMovy.setMovie(this);

		return groupVisibleMovy;
	}

	public GroupVisibleMovie removeGroupVisibleMovy(GroupVisibleMovie groupVisibleMovy) {
		getGroupVisibleMovies().remove(groupVisibleMovy);
		groupVisibleMovy.setMovie(null);

		return groupVisibleMovy;
	}

	public Set<MovieRelatedFolder> getMovieRelatedFolders() {
		return this.movieRelatedFolders;
	}

	public void setMovieRelatedFolders(Set<MovieRelatedFolder> movieRelatedFolders) {
		this.movieRelatedFolders = movieRelatedFolders;
	}

	public MovieRelatedFolder addMovieRelatedFolder(MovieRelatedFolder movieRelatedFolder) {
		getMovieRelatedFolders().add(movieRelatedFolder);
		movieRelatedFolder.setMovie(this);

		return movieRelatedFolder;
	}

	public MovieRelatedFolder removeMovieRelatedFolder(MovieRelatedFolder movieRelatedFolder) {
		getMovieRelatedFolders().remove(movieRelatedFolder);
		movieRelatedFolder.setMovie(null);

		return movieRelatedFolder;
	}

	public Set<MovieRelatedUser> getMovieRelatedUsers() {
		return this.movieRelatedUsers;
	}

	public void setMovieRelatedUsers(Set<MovieRelatedUser> movieRelatedUsers) {
		this.movieRelatedUsers = movieRelatedUsers;
	}

	public MovieRelatedUser addMovieRelatedUser(MovieRelatedUser movieRelatedUser) {
		getMovieRelatedUsers().add(movieRelatedUser);
		movieRelatedUser.setMovie(this);

		return movieRelatedUser;
	}

	public MovieRelatedUser removeMovieRelatedUser(MovieRelatedUser movieRelatedUser) {
		getMovieRelatedUsers().remove(movieRelatedUser);
		movieRelatedUser.setMovie(null);

		return movieRelatedUser;
	}

}