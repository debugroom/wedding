package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the movie database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="movie")
@NamedQuery(name="Movie.findAll", query="SELECT m FROM Movie m")
public class Movie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="movie_id", unique=true, nullable=false, length=10)
	private String movieId;

	@Column(name="file_path", length=2147483647)
	private String filePath;

	@Column(name="is_controled")
	private Boolean isControled;

	@Temporal(TemporalType.DATE)
	@Column(name="last_updated_date")
	private Date lastUpdatedDate;

	private Integer ver;

	//bi-directional many-to-one association to GroupVisibleMovie
	@OneToMany(mappedBy="movie")
	private Set<GroupVisibleMovie> groupVisibleMovies;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="movie")
	private Set<Message> messages;

	//bi-directional many-to-many association to Group
	@ManyToMany
	@JoinTable(
		name="group_visible_movie"
		, joinColumns={
			@JoinColumn(name="movie_id", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="group_id", nullable=false)
			}
		)
	private Set<Group> grps;

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

	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
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

	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public Message addMessage(Message message) {
		getMessages().add(message);
		message.setMovie(this);

		return message;
	}

	public Message removeMessage(Message message) {
		getMessages().remove(message);
		message.setMovie(null);

		return message;
	}

	public Set<Group> getGrps() {
		return this.grps;
	}

	public void setGrps(Set<Group> grps) {
		this.grps = grps;
	}

}
