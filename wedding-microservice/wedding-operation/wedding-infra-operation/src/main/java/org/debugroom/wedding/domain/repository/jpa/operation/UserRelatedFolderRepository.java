package org.debugroom.wedding.domain.repository.jpa.operation;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.entity.operation.UserRelatedFolder;
import org.debugroom.wedding.domain.entity.operation.UserRelatedFolderPK;

public interface UserRelatedFolderRepository 
	extends JpaRepository<UserRelatedFolder, UserRelatedFolderPK>{

}
