package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the grp database table.
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

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to Affiliation
	@OneToMany(mappedBy="grp")
	private Set<Affiliation> affiliations;

	//bi-directional many-to-one association to GroupFolder
	@OneToMany(mappedBy="grp")
	private Set<GroupFolder> groupFolders;

	//bi-directional many-to-one association to GroupNotification
	@OneToMany(mappedBy="grp")
	private Set<GroupNotification> groupNotifications;

	//bi-directional many-to-one association to GroupVisibleMovie
	@OneToMany(mappedBy="grp")
	private Set<GroupVisibleMovie> groupVisibleMovies;

	//bi-directional many-to-one association to GroupVisiblePhoto
	@OneToMany(mappedBy="grp")
	private Set<GroupVisiblePhoto> groupVisiblePhotos;

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

	public Set<GroupFolder> getGroupFolders() {
		return this.groupFolders;
	}

	public void setGroupFolders(Set<GroupFolder> groupFolders) {
		this.groupFolders = groupFolders;
	}

	public GroupFolder addGroupFolder(GroupFolder groupFolder) {
		getGroupFolders().add(groupFolder);
		groupFolder.setGrp(this);

		return groupFolder;
	}

	public GroupFolder removeGroupFolder(GroupFolder groupFolder) {
		getGroupFolders().remove(groupFolder);
		groupFolder.setGrp(null);

		return groupFolder;
	}

	public Set<GroupNotification> getGroupNotifications() {
		return this.groupNotifications;
	}

	public void setGroupNotifications(Set<GroupNotification> groupNotifications) {
		this.groupNotifications = groupNotifications;
	}

	public GroupNotification addGroupNotification(GroupNotification groupNotification) {
		getGroupNotifications().add(groupNotification);
		groupNotification.setGrp(this);

		return groupNotification;
	}

	public GroupNotification removeGroupNotification(GroupNotification groupNotification) {
		getGroupNotifications().remove(groupNotification);
		groupNotification.setGrp(null);

		return groupNotification;
	}

	public Set<GroupVisibleMovie> getGroupVisibleMovies() {
		return this.groupVisibleMovies;
	}

	public void setGroupVisibleMovies(Set<GroupVisibleMovie> groupVisibleMovies) {
		this.groupVisibleMovies = groupVisibleMovies;
	}

	public GroupVisibleMovie addGroupVisibleMovy(GroupVisibleMovie groupVisibleMovy) {
		getGroupVisibleMovies().add(groupVisibleMovy);
		groupVisibleMovy.setGrp(this);

		return groupVisibleMovy;
	}

	public GroupVisibleMovie removeGroupVisibleMovy(GroupVisibleMovie groupVisibleMovy) {
		getGroupVisibleMovies().remove(groupVisibleMovy);
		groupVisibleMovy.setGrp(null);

		return groupVisibleMovy;
	}

	public Set<GroupVisiblePhoto> getGroupVisiblePhotos() {
		return this.groupVisiblePhotos;
	}

	public void setGroupVisiblePhotos(Set<GroupVisiblePhoto> groupVisiblePhotos) {
		this.groupVisiblePhotos = groupVisiblePhotos;
	}

	public GroupVisiblePhoto addGroupVisiblePhoto(GroupVisiblePhoto groupVisiblePhoto) {
		getGroupVisiblePhotos().add(groupVisiblePhoto);
		groupVisiblePhoto.setGrp(this);

		return groupVisiblePhoto;
	}

	public GroupVisiblePhoto removeGroupVisiblePhoto(GroupVisiblePhoto groupVisiblePhoto) {
		getGroupVisiblePhotos().remove(groupVisiblePhoto);
		groupVisiblePhoto.setGrp(null);

		return groupVisiblePhoto;
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
