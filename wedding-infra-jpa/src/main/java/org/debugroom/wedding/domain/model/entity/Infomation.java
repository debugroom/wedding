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
@Table(name="infomation")
@NamedQuery(name="Infomation.findAll", query="SELECT i FROM Infomation i")
public class Infomation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="info_id", unique=true, nullable=false, length=8)
	private String infoId;

	@Column(length=2147483647)
	private String info;

	@Temporal(TemporalType.DATE)
	@Column(name="registrated_date")
	private Date registratedDate;

	@Temporal(TemporalType.DATE)
	@Column(name="release_date")
	private Date releaseDate;

	@Column(length=256)
	private String title;

	//bi-directional many-to-many association to Group
	@ManyToMany
	@JoinTable(
		name="group_notification"
		, joinColumns={
			@JoinColumn(name="info_id", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="group_id", nullable=false)
			}
		)
	private Set<Group> grps;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="infomations")
	private Set<User> usrs;

	public Infomation() {
	}

	public String getInfoId() {
		return this.infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Date getRegistratedDate() {
		return this.registratedDate;
	}

	public void setRegistratedDate(Date registratedDate) {
		this.registratedDate = registratedDate;
	}

	public Date getReleaseDate() {
		return this.releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Group> getGrps() {
		return this.grps;
	}

	public void setGrps(Set<Group> grps) {
		this.grps = grps;
	}

	public Set<User> getUsrs() {
		return this.usrs;
	}

	public void setUsrs(Set<User> usrs) {
		this.usrs = usrs;
	}

}