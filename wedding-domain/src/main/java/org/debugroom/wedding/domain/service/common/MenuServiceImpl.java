package org.debugroom.wedding.domain.service.common;

import java.util.List;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

import org.debugroom.wedding.domain.model.entity.Menu;
import org.debugroom.wedding.domain.model.entity.User;

import org.debugroom.wedding.domain.repository.common.MenuRepository;

@Service("menuService")
public class MenuServiceImpl implements MenuService{
	
	@Inject
	MenuRepository menuRepository;
	
	public List<Menu> getUsableMenu(User user){
		List<Menu> menuList = null;
		return null;
	}
}
