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
@Table(name="address")
@NamedQuery(name="Address.findAll", query="SELECT a FROM Address a")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id", unique=true, nullable=false, length=8)
	private String userId;

	@Column(length=255)
	private String address;

	@Column(name="post_cd", length=8)
	private String postCd;

	//bi-directional one-to-one association to User
	@OneToOne
	@JoinColumn(name="user_id", nullable=false, insertable=false, updatable=false)
	private User usr;

	public Address() {
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCd() {
		return this.postCd;
	}

	public void setPostCd(String postCd) {
		this.postCd = postCd;
	}

	public User getUsr() {
		return this.usr;
	}

	public void setUsr(User usr) {
		this.usr = usr;
	}

}