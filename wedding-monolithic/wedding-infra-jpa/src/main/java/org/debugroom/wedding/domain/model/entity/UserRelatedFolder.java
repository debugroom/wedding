package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the user_related_folder database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="user_related_folder")
@NamedQuery(name="UserRelatedFolder.findAll", query="SELECT u FROM UserRelatedFolder u")
public class UserRelatedFolder implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserRelatedFolderPK id;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to Folder
	@ManyToOne
	@JoinColumn(name="folder_id", nullable=false, insertable=false, updatable=false)
	private Folder folder;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false, insertable=false, updatable=false)
	private User usr;

	public UserRelatedFolder() {
	}

	public UserRelatedFolderPK getId() {
		return this.id;
	}

	public void setId(UserRelatedFolderPK id) {
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

	public Folder getFolder() {
		return this.folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public User getUsr() {
		return this.usr;
	}

	public void setUsr(User usr) {
		this.usr = usr;
	}

}
