package org.debugroom.wedding.domain.entity.operation;

import java.io.Serializable;
import javax.persistence.*;

import org.debugroom.wedding.domain.entity.operation.Folder.FolderBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the photo_related_user database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="photo_related_user")
@NamedQuery(name="org.debugroom.wedding.domain.entity.operation.PhotoRelatedUser.findAll", 
	query="SELECT p FROM PhotoRelatedUser p")
public class PhotoRelatedUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PhotoRelatedUserPK id;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to Photo
	@ManyToOne
	@JoinColumn(name="photo_id", nullable=false, insertable=false, updatable=false)
	private Photo photo;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false, insertable=false, updatable=false)
	private User usr;

	public PhotoRelatedUser() {
	}

	public PhotoRelatedUserPK getId() {
		return this.id;
	}

	public void setId(PhotoRelatedUserPK id) {
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

	public Photo getPhoto() {
		return this.photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public User getUsr() {
		return this.usr;
	}

	public void setUsr(User usr) {
		this.usr = usr;
	}

}