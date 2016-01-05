package org.debugroom.wedding.domain.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the grp database table.
 * 
 */
@Entity
@Table(name="grp")
@NamedQuery(name="Group.findAll", query="SELECT g FROM Group g")
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="group_id")
	private String groupId;

	@Column(name="group_name")
	private String groupName;

	//bi-directional many-to-one association to Affiliation
	@OneToMany(mappedBy="grp")
	private Set<Affiliation> affiliations;

	//bi-directional many-to-many association to Infomation
	@ManyToMany(mappedBy="grps")
	private Set<Infomation> infomations;

	//bi-directional many-to-many association to Movie
	@ManyToMany(mappedBy="grps")
	private Set<Movie> movies;

	//bi-directional many-to-many association to Photo
	@ManyToMany(mappedBy="grps")
	private Set<Photo> photos;

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

	public Set<Affiliation> getAffiliations() {
		return this.affiliations;
	}

	public void setAffiliations(Set<Affiliation> affiliations) {
		this.affiliations = affiliations;
	}

	public Affiliation addAffiliation(Affiliation affiliation) {
		getAffiliations().add(affiliation);
		affiliation.setGrp(this);

		return affiliation;
	}

	public Affiliation removeAffiliation(Affiliation affiliation) {
		getAffiliations().remove(affiliation);
		affiliation.setGrp(null);

		return affiliation;
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

}