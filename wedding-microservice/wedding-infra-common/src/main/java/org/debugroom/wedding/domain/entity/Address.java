package org.debugroom.wedding.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the address database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@NamedQuery(name="Address.findAll", query="SELECT a FROM Address a")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id")
	private String userId;

	private String address;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Column(name="post_cd")
	private String postCd;

	@Version
	private Integer ver;

	//bi-directional one-to-one association to User
	@OneToOne
	@JoinColumn(name="user_id")
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

	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getPostCd() {
		return this.postCd;
	}

	public void setPostCd(String postCd) {
		this.postCd = postCd;
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
