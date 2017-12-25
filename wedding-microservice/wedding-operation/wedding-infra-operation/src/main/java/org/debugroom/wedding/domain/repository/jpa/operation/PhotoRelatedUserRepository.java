package org.debugroom.wedding.domain.repository.jpa.operation;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.entity.operation.PhotoRelatedUser;
import org.debugroom.wedding.domain.entity.operation.PhotoRelatedUserPK;

public interface PhotoRelatedUserRepository  
	extends JpaRepository<PhotoRelatedUser, PhotoRelatedUserPK>{

}
