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

	//bi-directional many-to-many association to Infomation
	@ManyToMany
	@JoinTable(
		name="notification"
		, joinColumns={
			@JoinColumn(name="user_id", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="info_id", nullable=false)
			}
		)
	private Set<Infomation> infomations;

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

	public Set<Group> getGrps() {
		return this.grps;
	}

	public void setGrps(Set<Group> grps) {
		this.grps = grps;
	}

	public Set<Infomation> getInfomations() {
		return this.infomations;
	}

	public void setInfomations(Set<Infomation> infomations) {
		this.infomations = infomations;
	}

}