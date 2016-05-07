package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the photo database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="photo")
@NamedQuery(name="Photo.findAll", query="SELECT p FROM Photo p")
public class Photo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="photo_id", unique=true, nullable=false, length=10)
	private String photoId;

	@Column(name="file_path", length=2147483647)
	private String filePath;

	@Column(name="is_controled")
	private Boolean isControled;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Column(name="thumbnail_file_path", length=2147483647)
	private String thumbnailFilePath;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to GroupVisiblePhoto
	@OneToMany(mappedBy="photo")
	@JsonIgnore
	private Set<GroupVisiblePhoto> groupVisiblePhotos;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="photo")
	@JsonIgnore
	private Set<Message> messages;

	//bi-directional many-to-many association to Group
	@ManyToMany
	@JoinTable(
		name="group_visible_photo"
		, joinColumns={
			@JoinColumn(name="photo_id", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="group_id", nullable=false)
			}
		)
	@JsonIgnore
	private Set<Group> grps;

	//bi-directional many-to-one association to PhotoRelatedFolder
	@OneToMany(mappedBy="photo")
	@JsonIgnore
	private Set<PhotoRelatedFolder> photoRelatedFolders;

	//bi-directional many-to-one association to PhotoRelatedUser
	@OneToMany(mappedBy="photo")
	@JsonIgnore
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

	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public Message addMessage(Message message) {
		getMessages().add(message);
		message.setPhoto(this);

		return message;
	}

	public Message removeMessage(Message message) {
		getMessages().remove(message);
		message.setPhoto(null);

		return message;
	}

	public Set<Group> getGrps() {
		return this.grps;
	}

	public void setGrps(Set<Group> grps) {
		this.grps = grps;
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
