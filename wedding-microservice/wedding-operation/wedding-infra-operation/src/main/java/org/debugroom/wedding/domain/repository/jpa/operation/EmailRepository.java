package org.debugroom.wedding.domain.repository.jpa.operation;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.entity.Email;
import org.debugroom.wedding.domain.entity.EmailPK;

public interface EmailRepository extends JpaRepository<Email, EmailPK>{
}
