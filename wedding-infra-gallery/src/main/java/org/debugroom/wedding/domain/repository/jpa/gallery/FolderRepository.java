package org.debugroom.wedding.domain.repository.jpa.gallery;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.debugroom.wedding.domain.entity.gallery.Folder;

public interface FolderRepository extends JpaRepository<Folder, String>
											, JpaSpecificationExecutor<Folder>{
	
	public Folder findTopByOrderByFolderIdDesc();
	
}
