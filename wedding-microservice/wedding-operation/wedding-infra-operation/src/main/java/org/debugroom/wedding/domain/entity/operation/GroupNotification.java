package org.debugroom.wedding.domain.entity.operation;

import java.io.Serializable;
import javax.persistence.*;

import org.debugroom.wedding.domain.entity.operation.Folder.FolderBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the group_notification database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="group_notification")
@NamedQuery(name="org.debugroom.wedding.domain.entity.operation,GroupNotification.findAll", 
	query="SELECT g FROM GroupNotification g")
public class GroupNotification implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GroupNotificationPK id;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="group_id", nullable=false, insertable=false, updatable=false)
	private Group grp;

	public GroupNotification() {
	}

	public GroupNotificationPK getId() {
		return this.id;
	}

	public void setId(GroupNotificationPK id) {
		this.id = id;
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

	public Group getGrp() {
		return this.grp;
	}

	public void setGrp(Group grp) {
		this.grp = grp;
	}

}