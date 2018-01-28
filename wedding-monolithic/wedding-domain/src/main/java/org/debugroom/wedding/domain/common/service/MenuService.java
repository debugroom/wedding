package org.debugroom.wedding.domain.common.service;

import java.util.List;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.model.entity.Menu;
import org.debugroom.wedding.domain.model.entity.User;

public interface MenuService {

	public List<Menu> getUsableMenu(User user) throws BusinessException;
	
}
