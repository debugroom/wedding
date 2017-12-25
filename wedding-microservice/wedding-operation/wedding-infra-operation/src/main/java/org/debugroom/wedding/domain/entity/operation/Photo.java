package org.debugroom.wedding.domain.entity.operation;

import java.io.Serializable;
import javax.persistence.*;

import org.debugroom.wedding.domain.entity.operation.Folder.FolderBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the photo database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@NamedQuery(name="org.debugroom.wedding.domain.entity.operation.Photo.findAll", 
	query="SELECT p FROM Photo p")
public class Photo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="photo_id")
	private String photoId;

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

	//bi-directional many-to-one association to GroupVisiblePhoto
	@OneToMany(mappedBy="photo")
	private Set<GroupVisiblePhoto> groupVisiblePhotos;

	//bi-directional many-to-one association to PhotoRelatedFolder
	@OneToMany(mappedBy="photo")
	private Set<PhotoRelatedFolder> photoRelatedFolders;

	//bi-directional many-to-one association to PhotoRelatedUser
	@OneToMany(mappedBy="photo")
	private Set<PhotoRelatedUser> photoRelatedUsers;

	public Photo() {
	}

	public String getPhotoId() {
		return this.photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
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

	public Set<GroupVisiblePhoto> getGroupVisiblePhotos() {
		return this.groupVisiblePhotos;
	}

	public void setGroupVisiblePhotos(Set<GroupVisiblePhoto> groupVisiblePhotos) {
		this.groupVisiblePhotos = groupVisiblePhotos;
	}

	public GroupVisiblePhoto addGroupVisiblePhoto(GroupVisiblePhoto groupVisiblePhoto) {
		getGroupVisiblePhotos().add(groupVisiblePhoto);
		groupVisiblePhoto.setPhoto(this);

		return groupVisiblePhoto;
	}

	public GroupVisiblePhoto removeGroupVisiblePhoto(GroupVisiblePhoto groupVisiblePhoto) {
		getGroupVisiblePhotos().remove(groupVisiblePhoto);
		groupVisiblePhoto.setPhoto(null);

		return groupVisiblePhoto;
	}

	public Set<PhotoRelatedFolder> getPhotoRelatedFolders() {
		return this.photoRelatedFolders;
	}

	public void setPhotoRelatedFolders(Set<PhotoRelatedFolder> photoRelatedFolders) {
		this.photoRelatedFolders = photoRelatedFolders;
	}

	public PhotoRelatedFolder addPhotoRelatedFolder(PhotoRelatedFolder photoRelatedFolder) {
		getPhotoRelatedFolders().add(photoRelatedFolder);
		photoRelatedFolder.setPhoto(this);

		return photoRelatedFolder;
	}

	public PhotoRelatedFolder removePhotoRelatedFolder(PhotoRelatedFolder photoRelatedFolder) {
		getPhotoRelatedFolders().remove(photoRelatedFolder);
		photoRelatedFolder.setPhoto(null);

		return photoRelatedFolder;
	}

	public Set<PhotoRelatedUser> getPhotoRelatedUsers() {
		return this.photoRelatedUsers;
	}

	public void setPhotoRelatedUsers(Set<PhotoRelatedUser> photoRelatedUsers) {
		this.photoRelatedUsers = photoRelatedUsers;
	}

	public PhotoRelatedUser addPhotoRelatedUser(PhotoRelatedUser photoRelatedUser) {
		getPhotoRelatedUsers().add(photoRelatedUser);
		photoRelatedUser.setPhoto(this);

		return photoRelatedUser;
	}

	public PhotoRelatedUser removePhotoRelatedUser(PhotoRelatedUser photoRelatedUser) {
		getPhotoRelatedUsers().remove(photoRelatedUser);
		photoRelatedUser.setPhoto(null);

		return photoRelatedUser;
	}

}