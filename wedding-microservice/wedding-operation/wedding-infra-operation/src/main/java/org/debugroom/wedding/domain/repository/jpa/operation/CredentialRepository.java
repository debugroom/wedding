package org.debugroom.wedding.domain.repository.jpa.operation;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.entity.Credential;
import org.debugroom.wedding.domain.entity.CredentialPK;

public interface CredentialRepository extends JpaRepository<Credential, CredentialPK>{
}
