package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the notification database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="notification")
@NamedQuery(name="Notification.findAll", query="SELECT n FROM Notification n")
public class Notification implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NotificationPK id;

	@Column(name="is_accessed")
	private Boolean isAccessed;

	@Temporal(TemporalType.DATE)
	@Column(name="last_updated_date")
	private Date lastUpdatedDate;

	private Integer ver;

	//bi-directional many-to-one association to Infomation
	@ManyToOne
	@JoinColumn(name="info_id", nullable=false, insertable=false, updatable=false)
	private Infomation infomation;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false, insertable=false, updatable=false)
	private User usr;

	public Notification() {
	}

	public NotificationPK getId() {
		return this.id;
	}

	public void setId(NotificationPK id) {
		this.id = id;
	}

	public Boolean getIsAccessed() {
		return this.isAccessed;
	}

	public void setIsAccessed(Boolean isAccessed) {
		this.isAccessed = isAccessed;
	}

	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Integer getVer() {
		return this.ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public Infomation getInfomation() {
		return this.infomation;
	}

	public void setInfomation(Infomation infomation) {
		this.infomation = infomation;
	}

	public User getUsr() {
		return this.usr;
	}

	public void setUsr(User usr) {
		this.usr = usr;
	}

}
