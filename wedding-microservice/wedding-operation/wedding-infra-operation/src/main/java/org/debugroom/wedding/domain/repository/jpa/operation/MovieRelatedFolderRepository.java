package org.debugroom.wedding.domain.repository.jpa.operation;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.entity.operation.MovieRelatedFolder;
import org.debugroom.wedding.domain.entity.operation.MovieRelatedFolderPK;

public interface MovieRelatedFolderRepository 
	extends JpaRepository<MovieRelatedFolder, MovieRelatedFolderPK> {
}
