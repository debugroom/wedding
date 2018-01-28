package org.debugroom.wedding.domain.repository.jpa.operation;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.entity.operation.MovieRelatedUser;
import org.debugroom.wedding.domain.entity.operation.MovieRelatedUserPK;

public interface MovieRelatedUserRepository 
	extends JpaRepository<MovieRelatedUser, MovieRelatedUserPK>{

}
