package org.debugroom.wedding.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the information database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@NamedQuery(name="Information.findAll", query="SELECT i FROM Information i")
public class Information implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="info_id")
	private String infoId;

	@Column(name="info_page_path")
	private String infoPagePath;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Column(name="registrated_date")
	private Timestamp registratedDate;

	@Column(name="release_date")
	private Timestamp releaseDate;

	private String title;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to Notification
	@OneToMany(mappedBy="information")
	private Set<Notification> notifications;

	public Information() {
	}

	public String getInfoId() {
		return this.infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getInfoPagePath() {
		return this.infoPagePath;
	}

	public void setInfoPagePath(String infoPagePath) {
		this.infoPagePath = infoPagePath;
	}

	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Timestamp getRegistratedDate() {
		return this.registratedDate;
	}

	public void setRegistratedDate(Timestamp registratedDate) {
		this.registratedDate = registratedDate;
	}

	public Timestamp getReleaseDate() {
		return this.releaseDate;
	}

	public void setReleaseDate(Timestamp releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getVer() {
		return this.ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public Set<Notification> getNotifications() {
		return this.notifications;
	}

	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
	}

	public Notification addNotification(Notification notification) {
		getNotifications().add(notification);
		notification.setInformation(this);

		return notification;
	}

	public Notification removeNotification(Notification notification) {
		getNotifications().remove(notification);
		notification.setInformation(null);

		return notification;
	}

}
