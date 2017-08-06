package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the group_visible_movie database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="group_visible_movie")
@NamedQuery(name="GroupVisibleMovie.findAll", query="SELECT g FROM GroupVisibleMovie g")
public class GroupVisibleMovie implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GroupVisibleMoviePK id;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="group_id", nullable=false, insertable=false, updatable=false)
	private Group grp;

	//bi-directional many-to-one association to Movie
	@ManyToOne
	@JoinColumn(name="movie_id", nullable=false, insertable=false, updatable=false)
	private Movie movie;

	public GroupVisibleMovie() {
	}

	public GroupVisibleMoviePK getId() {
		return this.id;
	}

	public void setId(GroupVisibleMoviePK id) {
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

	public Group getGrp() {
		return this.grp;
	}

	public void setGrp(Group grp) {
		this.grp = grp;
	}

	public Movie getMovie() {
		return this.movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

}
