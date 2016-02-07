package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the group_notification database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="group_notification")
@NamedQuery(name="GroupNotification.findAll", query="SELECT g FROM GroupNotification g")
public class GroupNotification implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GroupNotificationPK id;

	@Temporal(TemporalType.DATE)
	@Column(name="last_updated_date")
	private Date lastUpdatedDate;

	private Integer ver;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="group_id", nullable=false, insertable=false, updatable=false)
	private Group grp;

	//bi-directional many-to-one association to Infomation
	@ManyToOne
	@JoinColumn(name="info_id", nullable=false, insertable=false, updatable=false)
	private Infomation infomation;

	public GroupNotification() {
	}

	public GroupNotificationPK getId() {
		return this.id;
	}

	public void setId(GroupNotificationPK id) {
		this.id = id;
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

	public Group getGrp() {
		return this.grp;
	}

	public void setGrp(Group grp) {
		this.grp = grp;
	}

	public Infomation getInfomation() {
		return this.infomation;
	}

	public void setInfomation(Infomation infomation) {
		this.infomation = infomation;
	}

}
