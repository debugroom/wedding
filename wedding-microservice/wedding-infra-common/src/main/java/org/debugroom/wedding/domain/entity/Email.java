package org.debugroom.wedding.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;


/**
 * The persistent class for the email database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@NamedQuery(name="Email.findAll", query="SELECT e FROM Email e")
public class Email implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmailPK id;

	private String email;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false, insertable=false, updatable=false)
	private User usr;

	public Email() {
	}

	public EmailPK getId() {
		return this.id;
	}

	public void setId(EmailPK id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
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
