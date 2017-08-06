package org.debugroom.wedding.domain.entity.gallery;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;


/**
 * The persistent class for the photo_related_folder database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="photo_related_folder")
@NamedQuery(name="PhotoRelatedFolder.findAll", query="SELECT p FROM PhotoRelatedFolder p")
public class PhotoRelatedFolder implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PhotoRelatedFolderPK id;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to Folder
	@ManyToOne
	@JoinColumn(name="folder_id", nullable=false, insertable=false, updatable=false)
	private Folder folder;

	//bi-directional many-to-one association to Photo
	@ManyToOne
	@JoinColumn(name="photo_id", nullable=false, insertable=false, updatable=false)
	private Photo photo;

	public PhotoRelatedFolder() {
	}

	public PhotoRelatedFolderPK getId() {
		return this.id;
	}

	public void setId(PhotoRelatedFolderPK id) {
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

	@JsonIgnore
	public Folder getFolder() {
		return this.folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	@JsonIgnore
	public Photo getPhoto() {
		return this.photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

}
