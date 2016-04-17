package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the infomation database table.
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

	@Column(name="info_page_path", length=2147483647)
	private String infoPagePath;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Column(name="registrated_date")
	private Timestamp registratedDate;

	@Column(name="release_date")
	private Timestamp releaseDate;

	@Column(length=256)
	private String title;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to GroupNotification
	@OneToMany(mappedBy="infomation", cascade=CascadeType.ALL)
	private Set<GroupNotification> groupNotifications;

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

	//bi-directional many-to-one association to Notification
	@OneToMany(mappedBy="infomation", cascade=CascadeType.ALL)
	private Set<Notification> notifications;

	public Infomation() {
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

	public Set<GroupNotification> getGroupNotifications() {
		return this.groupNotifications;
	}

	public void setGroupNotifications(Set<GroupNotification> groupNotifications) {
		this.groupNotifications = groupNotifications;
	}

	public GroupNotification addGroupNotification(GroupNotification groupNotification) {
		getGroupNotifications().add(groupNotification);
		groupNotification.setInfomation(this);

		return groupNotification;
	}

	public GroupNotification removeGroupNotification(GroupNotification groupNotification) {
		getGroupNotifications().remove(groupNotification);
		groupNotification.setInfomation(null);

		return groupNotification;
	}

	public Set<Group> getGrps() {
		return this.grps;
	}

	public void setGrps(Set<Group> grps) {
		this.grps = grps;
	}

	public Set<Notification> getNotifications() {
		return this.notifications;
	}

	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
	}

	public Notification addNotification(Notification notification) {
		getNotifications().add(notification);
		notification.setInfomation(this);

		return notification;
	}

	public Notification removeNotification(Notification notification) {
		getNotifications().remove(notification);
		notification.setInfomation(null);

		return notification;
	}

}
