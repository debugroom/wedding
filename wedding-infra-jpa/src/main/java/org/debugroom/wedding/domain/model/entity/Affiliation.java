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
@Table(name="affiliation")
@NamedQuery(name="Affiliation.findAll", query="SELECT a FROM Affiliation a")
public class Affiliation implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AffiliationPK id;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="group_id", nullable=false, insertable=false, updatable=false)
	private Group grp;

	public Affiliation() {
	}

	public AffiliationPK getId() {
		return this.id;
	}

	public void setId(AffiliationPK id) {
		this.id = id;
	}

	public Group getGrp() {
		return this.grp;
	}

	public void setGrp(Group grp) {
		this.grp = grp;
	}

}