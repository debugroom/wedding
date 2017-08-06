package org.debugroom.wedding.domain.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.model.entity.Menu;
import org.springframework.cache.annotation.Cacheable;

public interface MenuRepository extends JpaRepository<Menu, String> {
	
	@Cacheable(cacheNames="menus")
	List<Menu> findByAuthorityLevelLessThanOrderByMenuIdAsc(int authorityLevel);
	
}
