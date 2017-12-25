package org.debugroom.wedding.domain.repository.jpa.operation;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.entity.operation.GroupVisibleMovie;
import org.debugroom.wedding.domain.entity.operation.GroupVisibleMoviePK;

public interface GroupVisibleMovieRepository 
	extends JpaRepository<GroupVisibleMovie, GroupVisibleMoviePK>{
}
