package org.debugroom.wedding.domain.repository.jpa.operation;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.entity.operation.GroupVisiblePhoto;
import org.debugroom.wedding.domain.entity.operation.GroupVisiblePhotoPK;

public interface GroupVisiblePhotoRepository 
	extends JpaRepository<GroupVisiblePhoto, GroupVisiblePhotoPK>{
}
