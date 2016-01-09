package org.debugroom.wedding.domain.model.entity;

import java.io.Serializable;
import javax.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * The persistent class for the address database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="email")
@NamedQuery(name="Email.findAll", query="SELECT e FROM Email e")
public class Email implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmailPK id;

	@Column(length=100)
	private String email;

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

	public User getUsr() {
		return this.usr;
	}

	public void setUsr(User usr) {
		this.usr = usr;
	}

}