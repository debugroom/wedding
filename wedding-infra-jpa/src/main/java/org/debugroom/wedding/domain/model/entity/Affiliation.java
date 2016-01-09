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

	public Affiliation() {
	}

	public AffiliationPK getId() {
		return this.id;
	}

	public void setId(AffiliationPK id) {
		this.id = id;
	}

}