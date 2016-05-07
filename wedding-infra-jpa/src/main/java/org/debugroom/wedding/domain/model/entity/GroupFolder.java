package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the group_folder database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="group_folder")
@NamedQuery(name="GroupFolder.findAll", query="SELECT g FROM GroupFolder g")
public class GroupFolder implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GroupFolderPK id;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to Folder
	@ManyToOne
	@JoinColumn(name="folder_id", nullable=false, insertable=false, updatable=false)
	private Folder folder;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="group_id", nullable=false, insertable=false, updatable=false)
	private Group grp;

	public GroupFolder() {
	}

	public GroupFolderPK getId() {
		return this.id;
	}

	public void setId(GroupFolderPK id) {
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

	public Group getGrp() {
		return this.grp;
	}

	public void setGrp(Group grp) {
		this.grp = grp;
	}

}
