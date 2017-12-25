package org.debugroom.wedding.domain.entity.operation;

import java.io.Serializable;
import javax.persistence.*;

import org.debugroom.wedding.domain.entity.operation.UserRelatedFolder.UserRelatedFolderBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the usr database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="usr")
@NamedQuery(name="org.debugroom.wedding.domain.entity.operation.User.findAll", 
	query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id")
	private String userId;

	@Column(name="authority_level")
	private Integer authorityLevel;

	@Column(name="first_name")
	private String firstName;

	@Column(name="image_file_path")
	private String imageFilePath;

	@Column(name="is_bride_side")
	private Boolean isBrideSide;

	@Column(name="last_login_date")
	private Timestamp lastLoginDate;

	@Column(name="last_name")
	private String lastName;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Column(name="login_id")
	private String loginId;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to Folder
	@OneToMany(mappedBy="usr")
	private Set<Folder> folders;

	//bi-directional many-to-one association to MovieRelatedUser
	@OneToMany(mappedBy="usr")
	private Set<MovieRelatedUser> movieRelatedUsers;

	//bi-directional many-to-one association to PhotoRelatedUser
	@OneToMany(mappedBy="usr")
	private Set<PhotoRelatedUser> photoRelatedUsers;

	//bi-directional many-to-one association to UserRelatedFolder
	@OneToMany(mappedBy="usr")
	private Set<UserRelatedFolder> userRelatedFolders;

	//bi-directional many-to-one association to RequestStatus
	@OneToMany(mappedBy="usr")
	private Set<RequestStatus> requestStatuses;

	public User() {
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getAuthorityLevel() {
		return this.authorityLevel;
	}

	public void setAuthorityLevel(Integer authorityLevel) {
		this.authorityLevel = authorityLevel;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getImageFilePath() {
		return this.imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	public Boolean getIsBrideSide() {
		return this.isBrideSide;
	}

	public void setIsBrideSide(Boolean isBrideSide) {
		this.isBrideSide = isBrideSide;
	}

	public Timestamp getLastLoginDate() {
		return this.lastLoginDate;
	}

	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Integer getVer() {
		return this.ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public Set<Folder> getFolders() {
		return this.folders;
	}

	public void setFolders(Set<Folder> folders) {
		this.folders = folders;
	}

	public Folder addFolder(Folder folder) {
		getFolders().add(folder);
		folder.setUsr(this);

		return folder;
	}

	public Folder removeFolder(Folder folder) {
		getFolders().remove(folder);
		folder.setUsr(null);

		return folder;
	}

	public Set<MovieRelatedUser> getMovieRelatedUsers() {
		return this.movieRelatedUsers;
	}

	public void setMovieRelatedUsers(Set<MovieRelatedUser> movieRelatedUsers) {
		this.movieRelatedUsers = movieRelatedUsers;
	}

	public MovieRelatedUser addMovieRelatedUser(MovieRelatedUser movieRelatedUser) {
		getMovieRelatedUsers().add(movieRelatedUser);
		movieRelatedUser.setUsr(this);

		return movieRelatedUser;
	}

	public MovieRelatedUser removeMovieRelatedUser(MovieRelatedUser movieRelatedUser) {
		getMovieRelatedUsers().remove(movieRelatedUser);
		movieRelatedUser.setUsr(null);

		return movieRelatedUser;
	}

	public Set<PhotoRelatedUser> getPhotoRelatedUsers() {
		return this.photoRelatedUsers;
	}

	public void setPhotoRelatedUsers(Set<PhotoRelatedUser> photoRelatedUsers) {
		this.photoRelatedUsers = photoRelatedUsers;
	}

	public PhotoRelatedUser addPhotoRelatedUser(PhotoRelatedUser photoRelatedUser) {
		getPhotoRelatedUsers().add(photoRelatedUser);
		photoRelatedUser.setUsr(this);

		return photoRelatedUser;
	}

	public PhotoRelatedUser removePhotoRelatedUser(PhotoRelatedUser photoRelatedUser) {
		getPhotoRelatedUsers().remove(photoRelatedUser);
		photoRelatedUser.setUsr(null);

		return photoRelatedUser;
	}

	public Set<UserRelatedFolder> getUserRelatedFolders() {
		return this.userRelatedFolders;
	}

	public void setUserRelatedFolders(Set<UserRelatedFolder> userRelatedFolders) {
		this.userRelatedFolders = userRelatedFolders;
	}

	public UserRelatedFolder addUserRelatedFolder(UserRelatedFolder userRelatedFolder) {
		getUserRelatedFolders().add(userRelatedFolder);
		userRelatedFolder.setUsr(this);

		return userRelatedFolder;
	}

	public UserRelatedFolder removeUserRelatedFolder(UserRelatedFolder userRelatedFolder) {
		getUserRelatedFolders().remove(userRelatedFolder);
		userRelatedFolder.setUsr(null);

		return userRelatedFolder;
	}

	public Set<RequestStatus> getRequestStatuses() {
		return this.requestStatuses;
	}

	public void setRequestStatuses(Set<RequestStatus> requestStatuses) {
		this.requestStatuses = requestStatuses;
	}

	public RequestStatus addRequestStatus(RequestStatus requestStatus) {
		getRequestStatuses().add(requestStatus);
		requestStatus.setUsr(this);

		return requestStatus;
	}

	public RequestStatus removeRequestStatus(RequestStatus requestStatus) {
		getRequestStatuses().remove(requestStatus);
		requestStatus.setUsr(null);

		return requestStatus;
	}

}