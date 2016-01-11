package org.debugroom.wedding.domain.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
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

	@Temporal(TemporalType.DATE)
	@Column(name="last_login_date")
	private Date lastLoginDate;

	@Column(name="login_id", length=2147483647)
	private String loginId;

	@Column(name="user_name", length=2147483647)
	private String userName;

	//bi-directional one-to-one association to Address
	@OneToOne(mappedBy="usr")
	private Address address;

	//bi-directional many-to-one association to Credential
	@OneToMany(mappedBy="usr")
	private Set<Credential> credentials;

	//bi-directional many-to-one association to Email
	@OneToMany(mappedBy="usr")
	private Set<Email> emails;

	//bi-directional many-to-one association to Notification
	@OneToMany(mappedBy="usr")
	private Set<Notification> notifications;

	//bi-directional many-to-one association to RequestStatus
	@OneToMany(mappedBy="usr")
	private Set<RequestStatus> requestStatuses;

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
	private Set<Group> grps;

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

	public Date getLastLoginDate() {
		return this.lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
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

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
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

	public Set<Group> getGrps() {
		return this.grps;
	}

	public void setGrps(Set<Group> grps) {
		this.grps = grps;
	}

}