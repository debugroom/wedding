package org.debugroom.wedding.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;


/**
 * The persistent class for the credential database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@NamedQuery(name="Credential.findAll", query="SELECT c FROM Credential c")
public class Credential implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CredentialPK id;

	@Column(name="credential_key")
	private String credentialKey;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Column(name="valid_date")
	private Timestamp validDate;

	@Version
	private Integer ver;

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

	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Timestamp getValidDate() {
		return this.validDate;
	}

	public void setValidDate(Timestamp validDate) {
		this.validDate = validDate;
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

}
