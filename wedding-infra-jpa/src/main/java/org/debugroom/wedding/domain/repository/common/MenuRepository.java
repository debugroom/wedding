package org.debugroom.wedding.domain.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.model.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, String> {
	
	List<Menu> findByAuthorityLevelLessThanOrderByMenuIdAsc(int authorityLevel);
	
}
