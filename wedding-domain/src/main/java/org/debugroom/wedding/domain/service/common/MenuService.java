package org.debugroom.wedding.domain.service.common;

import java.util.List;

import org.debugroom.wedding.domain.model.entity.Menu;
import org.debugroom.wedding.domain.model.entity.User;

public interface MenuService {

	public List<Menu> getUsableMenu(User user);
	
}
