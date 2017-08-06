package org.debugroom.wedding.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the grp database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="grp")
@NamedQuery(name="Group.findAll", query="SELECT g FROM Group g")
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="group_id")
	private String groupId;

	@Column(name="group_name")
	private String groupName;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to Affiliation
	@OneToMany(mappedBy="grp")
	private Set<Affiliation> affiliations;

	public Group() {
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public Set<Affiliation> getAffiliations() {
		return this.affiliations;
	}

	public void setAffiliations(Set<Affiliation> affiliations) {
		this.affiliations = affiliations;
	}

	public Affiliation addAffiliation(Affiliation affiliation) {
		getAffiliations().add(affiliation);
		affiliation.setGrp(this);

		return affiliation;
	}

	public Affiliation removeAffiliation(Affiliation affiliation) {
		getAffiliations().remove(affiliation);
		affiliation.setGrp(null);

		return affiliation;
	}

}
