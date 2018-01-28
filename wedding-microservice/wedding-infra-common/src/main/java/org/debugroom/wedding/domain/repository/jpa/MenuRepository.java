package org.debugroom.wedding.domain.repository.jpa;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, String>{

	@Cacheable(cacheNames="menus")
	List<Menu> findByAuthorityLevelLessThanOrderByMenuIdAsc(int autorityLevel);

}
