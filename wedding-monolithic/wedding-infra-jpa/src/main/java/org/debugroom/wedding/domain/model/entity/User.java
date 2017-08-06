package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the usr database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="usr")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id", unique=true, nullable=false, length=8)
	private String userId;

	@Column(name="authority_level")
	private Integer authorityLevel;

	@Column(name="image_file_path", length=2147483647)
	private String imageFilePath;

	@Column(name="last_login_date")
	private Timestamp lastLoginDate;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Column(name="login_id", length=2147483647)
	private String loginId;

	@Column(name="user_name", length=2147483647)
	private String userName;

	@Version
	private Integer ver;

	//bi-directional one-to-one association to Address
	@OneToOne(optional = false, mappedBy="usr" , cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JsonIgnore
	private Address address;

	//bi-directional many-to-one association to Affiliation
	@OneToMany(mappedBy="usr")
	@JsonIgnore
	private Set<Affiliation> affiliations;

	//bi-directional many-to-one association to Credential
	@OneToMany(mappedBy="usr", cascade=CascadeType.ALL)
	@JsonIgnore
	private Set<Credential> credentials;

	//bi-directional many-to-one association to Email
	@OneToMany(mappedBy="usr", cascade=CascadeType.ALL)
	@JsonIgnore
	private Set<Email> emails;

	//bi-directional many-to-one association to Folder
	@OneToMany(mappedBy="usr")
	@JsonIgnore
	private Set<Folder> folders;

	//bi-directional many-to-one association to Notification
	@OneToMany(mappedBy="usr")
	@JsonIgnore
	private Set<Notification> notifications;

	//bi-directional many-to-one association to RequestStatus
	@OneToMany(mappedBy="usr")
	@JsonIgnore
	private Set<RequestStatus> requestStatuses;

	//bi-directional many-to-one association to UserRelatedFolder
	@OneToMany(mappedBy="usr")
	@JsonIgnore
	private Set<UserRelatedFolder> userRelatedFolders;

	//bi-directional many-to-many association to Group
	@ManyToMany
	@JoinTable(
		name="affiliation"
		, joinColumns={
			@JoinColumn(name="user_id", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="group_id", nullable=false)
			}
		)
	@JsonIgnore
	private Set<Group> grps;

	//bi-directional many-to-one association to MovieRelatedUser
	@OneToMany(mappedBy="usr")
	@JsonIgnore
	private Set<MovieRelatedUser> movieRelatedUsers;

	//bi-directional many-to-one association to PhotoRelatedUser
	@OneToMany(mappedBy="usr")
	@JsonIgnore
	private Set<PhotoRelatedUser> photoRelatedUsers;

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

	public String getImageFilePath() {
		return this.imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	public Timestamp getLastLoginDate() {
		return this.lastLoginDate;
	}

	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
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

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getVer() {
		return this.ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<Affiliation> getAffiliations() {
		return this.affiliations;
	}

	public void setAffiliations(Set<Affiliation> affiliations) {
		this.affiliations = affiliations;
	}

	public Affiliation addAffiliation(Affiliation affiliation) {
		getAffiliations().add(affiliation);
		affiliation.setUsr(this);

		return affiliation;
	}

	public Affiliation removeAffiliation(Affiliation affiliation) {
		getAffiliations().remove(affiliation);
		affiliation.setUsr(null);

		return affiliation;
	}

	public Set<Credential> getCredentials() {
		return this.credentials;
	}

	public void setCredentials(Set<Credential> credentials) {
		this.credentials = credentials;
	}

	public Credential addCredential(Credential credential) {
		getCredentials().add(credential);
		credential.setUsr(this);

		return credential;
	}

	public Credential removeCredential(Credential credential) {
		getCredentials().remove(credential);
		credential.setUsr(null);

		return credential;
	}

	public Set<Email> getEmails() {
		return this.emails;
	}

	public void setEmails(Set<Email> emails) {
		this.emails = emails;
	}

	public Email addEmail(Email email) {
		getEmails().add(email);
		email.setUsr(this);

		return email;
	}

	public Email removeEmail(Email email) {
		getEmails().remove(email);
		email.setUsr(null);

		return email;
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

	public Set<Notification> getNotifications() {
		return this.notifications;
	}

	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
	}

	public Notification addNotification(Notification notification) {
		getNotifications().add(notification);
		notification.setUsr(this);

		return notification;
	}

	public Notification removeNotification(Notification notification) {
		getNotifications().remove(notification);
		notification.setUsr(null);

		return notification;
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

	public Set<Group> getGrps() {
		return this.grps;
	}

	public void setGrps(Set<Group> grps) {
		this.grps = grps;
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

}
