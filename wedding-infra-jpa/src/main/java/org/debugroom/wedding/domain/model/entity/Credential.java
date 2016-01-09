package org.debugroom.wedding.domain.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * The persistent class for the address database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="credential")
@NamedQuery(name="Credential.findAll", query="SELECT c FROM Credential c")
public class Credential implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CredentialPK id;

	@Column(name="credential_key", length=2147483647)
	private String credentialKey;

	@Temporal(TemporalType.DATE)
	@Column(name="valid_date")
	private Date validDate;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false, insertable=false, updatable=false)
	private User usr;

	public Credential() {
	}

	public CredentialPK getId() {
		return this.id;
	}

	public void setId(CredentialPK id) {
		this.id = id;
	}

	public String getCredentialKey() {
		return this.credentialKey;
	}

	public void setCredentialKey(String credentialKey) {
		this.credentialKey = credentialKey;
	}

	public Date getValidDate() {
		return this.validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public User getUsr() {
		return this.usr;
	}

	public void setUsr(User usr) {
		this.usr = usr;
	}

}