package org.debugroom.wedding.domain.service.common;

import java.util.List;

import org.debugroom.framework.common.exception.BusinessException;

import org.debugroom.wedding.domain.entity.Menu;
import org.debugroom.wedding.domain.entity.User;

public interface MenuSharedService {

	public List<Menu> getUsableMenu(User user) throws BusinessException;
}
