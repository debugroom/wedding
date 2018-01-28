package org.debugroom.wedding.domain.entity.operation;

import java.io.Serializable;
import javax.persistence.*;

import org.debugroom.wedding.domain.entity.operation.Folder.FolderBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;

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
@NamedQuery(name="org.debugroom.wedding.domain.entity.operation.Group.findAll", 
	query="SELECT g FROM Group g")
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="group_id")
	private String groupId;

	@Column(name="group_name")
	private String groupName;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Version
	private Integer ver;

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

}