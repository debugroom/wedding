package org.debugroom.wedding.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
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
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id")
	private String userId;

	@Column(name="authority_level")
	private Integer authorityLevel;

	@Column(name="is_bride_side")
	private boolean isBrideSide;
	
	@Column(name="image_file_path")
	private String imageFilePath;

	@Column(name="last_login_date")
	private Timestamp lastLoginDate;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Column(name="login_id")
	private String loginId;

	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	@Version
	private Integer ver;

	//bi-directional one-to-one association to Address
	@OneToOne(mappedBy="usr")
	private Address address;

	//bi-directional many-to-one association to Affiliation
	@OneToMany(mappedBy="usr")
	private Set<Affiliation> affiliations;

	//bi-directional many-to-one association to Credential
	@OneToMany(mappedBy="usr")
	private Set<Credential> credentials;

	//bi-directional many-to-one association to Email
	@OneToMany(mappedBy="usr")
	private Set<Email> emails;

	//bi-directional many-to-one association to Notification
	@OneToMany(mappedBy="usr")
	private Set<Notification> notifications;

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

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public boolean isBrideSide() {
		return isBrideSide;
	}

	public void setBrideSide(boolean isBrideSide) {
		this.isBrideSide = isBrideSide;
	}

}
