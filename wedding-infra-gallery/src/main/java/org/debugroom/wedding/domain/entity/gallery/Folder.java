package org.debugroom.wedding.domain.entity.gallery;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the folder database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@NamedQuery(name="Folder.findAll", query="SELECT f FROM Folder f")
public class Folder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="folder_id")
	private String folderId;

	@Column(name="folder_name")
	private String folderName;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Column(name="parent_folder_id")
	private String parentFolderId;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User usr;

	//bi-directional many-to-one association to GroupFolder
	@OneToMany(mappedBy="folder", fetch=FetchType.EAGER)
	private Set<GroupFolder> groupFolders;

	//bi-directional many-to-one association to MovieRelatedFolder
	@OneToMany(mappedBy="folder", fetch=FetchType.EAGER)
	private Set<MovieRelatedFolder> movieRelatedFolders;

	//bi-directional many-to-one association to PhotoRelatedFolder
	@OneToMany(mappedBy="folder", fetch=FetchType.EAGER)
	private Set<PhotoRelatedFolder> photoRelatedFolders;

	//bi-directional many-to-one association to UserRelatedFolder
	@OneToMany(mappedBy="folder", cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<UserRelatedFolder> userRelatedFolders;

	public Folder() {
	}

	public String getFolderId() {
		return this.folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getFolderName() {
		return this.folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getParentFolderId() {
		return this.parentFolderId;
	}

	public void setParentFolderId(String parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	public Integer getVer() {
		return this.ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	@JsonIgnore
	public User getUsr() {
		return this.usr;
	}

	public void setUsr(User usr) {
		this.usr = usr;
	}

	public Set<GroupFolder> getGroupFolders() {
		return this.groupFolders;
	}

	public void setGroupFolders(Set<GroupFolder> groupFolders) {
		this.groupFolders = groupFolders;
	}

	public GroupFolder addGroupFolder(GroupFolder groupFolder) {
		getGroupFolders().add(groupFolder);
		groupFolder.setFolder(this);

		return groupFolder;
	}

	public GroupFolder removeGroupFolder(GroupFolder groupFolder) {
		getGroupFolders().remove(groupFolder);
		groupFolder.setFolder(null);

		return groupFolder;
	}

	public Set<MovieRelatedFolder> getMovieRelatedFolders() {
		return this.movieRelatedFolders;
	}

	public void setMovieRelatedFolders(Set<MovieRelatedFolder> movieRelatedFolders) {
		this.movieRelatedFolders = movieRelatedFolders;
	}

	public MovieRelatedFolder addMovieRelatedFolder(MovieRelatedFolder movieRelatedFolder) {
		getMovieRelatedFolders().add(movieRelatedFolder);
		movieRelatedFolder.setFolder(this);

		return movieRelatedFolder;
	}

	public MovieRelatedFolder removeMovieRelatedFolder(MovieRelatedFolder movieRelatedFolder) {
		getMovieRelatedFolders().remove(movieRelatedFolder);
		movieRelatedFolder.setFolder(null);

		return movieRelatedFolder;
	}

	public Set<PhotoRelatedFolder> getPhotoRelatedFolders() {
		return this.photoRelatedFolders;
	}

	public void setPhotoRelatedFolders(Set<PhotoRelatedFolder> photoRelatedFolders) {
		this.photoRelatedFolders = photoRelatedFolders;
	}

	public PhotoRelatedFolder addPhotoRelatedFolder(PhotoRelatedFolder photoRelatedFolder) {
		getPhotoRelatedFolders().add(photoRelatedFolder);
		photoRelatedFolder.setFolder(this);

		return photoRelatedFolder;
	}

	public PhotoRelatedFolder removePhotoRelatedFolder(PhotoRelatedFolder photoRelatedFolder) {
		getPhotoRelatedFolders().remove(photoRelatedFolder);
		photoRelatedFolder.setFolder(null);

		return photoRelatedFolder;
	}

	public Set<UserRelatedFolder> getUserRelatedFolders() {
		return this.userRelatedFolders;
	}

	public void setUserRelatedFolders(Set<UserRelatedFolder> userRelatedFolders) {
		this.userRelatedFolders = userRelatedFolders;
	}

	public UserRelatedFolder addUserRelatedFolder(UserRelatedFolder userRelatedFolder) {
		getUserRelatedFolders().add(userRelatedFolder);
		userRelatedFolder.setFolder(this);

		return userRelatedFolder;
	}

	public UserRelatedFolder removeUserRelatedFolder(UserRelatedFolder userRelatedFolder) {
		getUserRelatedFolders().remove(userRelatedFolder);
		userRelatedFolder.setFolder(null);

		return userRelatedFolder;
	}

}
