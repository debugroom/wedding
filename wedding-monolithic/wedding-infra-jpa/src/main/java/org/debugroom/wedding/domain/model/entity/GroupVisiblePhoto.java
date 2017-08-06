package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the group_visible_photo database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="group_visible_photo")
@NamedQuery(name="GroupVisiblePhoto.findAll", query="SELECT g FROM GroupVisiblePhoto g")
public class GroupVisiblePhoto implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GroupVisiblePhotoPK id;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="group_id", nullable=false, insertable=false, updatable=false)
	private Group grp;

	//bi-directional many-to-one association to Photo
	@ManyToOne
	@JoinColumn(name="photo_id", nullable=false, insertable=false, updatable=false)
	private Photo photo;

	public GroupVisiblePhoto() {
	}

	public GroupVisiblePhotoPK getId() {
		return this.id;
	}

	public void setId(GroupVisiblePhotoPK id) {
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

	public Photo getPhoto() {
		return this.photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

}
