package org.debugroom.wedding.domain.repository.jpa.operation;

import org.debugroom.wedding.domain.entity.Affiliation;
import org.debugroom.wedding.domain.entity.AffiliationPK;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AffiliationRepository extends JpaRepository<Affiliation, AffiliationPK>{
}
