package org.debugroom.wedding.domain.repository.common;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.debugroom.wedding.domain.model.entity.Folder;

public interface FolderRepository extends JpaRepository<Folder, String>
											, JpaSpecificationExecutor<Folder>{

}
