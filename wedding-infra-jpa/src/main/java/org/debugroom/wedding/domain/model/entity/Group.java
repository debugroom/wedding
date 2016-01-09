package org.debugroom.wedding.domain.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * The persistent class for the address database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="grp")
@NamedQuery(name="Group.findAll", query="SELECT g FROM Group g")
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="group_id", unique=true, nullable=false, length=10)
	private String groupId;

	@Column(name="group_name", length=256)
	private String groupName;

	//bi-directional many-to-many association to Infomation
	@ManyToMany(mappedBy="grps")
	private Set<Infomation> infomations;

	//bi-directional many-to-many association to Movie
	@ManyToMany(mappedBy="grps")
	private Set<Movie> movies;

	//bi-directional many-to-many association to Photo
	@ManyToMany(mappedBy="grps")
	private Set<Photo> photos;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="grps")
	private Set<User> usrs;

	public Group() {
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Set<Infomation> getInfomations() {
		return this.infomations;
	}

	public void setInfomations(Set<Infomation> infomations) {
		this.infomations = infomations;
	}

	public Set<Movie> getMovies() {
		return this.movies;
	}

	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}

	public Set<Photo> getPhotos() {
		return this.photos;
	}

	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}

	public Set<User> getUsrs() {
		return this.usrs;
	}

	public void setUsrs(Set<User> usrs) {
		this.usrs = usrs;
	}

}