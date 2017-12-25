package org.debugroom.wedding.domain.repository.jpa.operation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.debugroom.wedding.domain.entity.operation.PhotoRelatedFolder;
import org.debugroom.wedding.domain.entity.operation.PhotoRelatedFolderPK;

public interface PhotoRelatedFolderRepository 
	extends JpaRepository<PhotoRelatedFolder, PhotoRelatedFolderPK>{

}
