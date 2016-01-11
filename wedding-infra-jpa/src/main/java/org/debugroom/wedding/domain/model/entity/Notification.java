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
@Table(name="notification")
@NamedQuery(name="Notification.findAll", query="SELECT n FROM Notification n")
public class Notification implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NotificationPK id;

	@Column(name="is_accessed")
	private Boolean isAccessed;

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