package org.debugroom.wedding.domain.service.common;

import java.util.List;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.model.entity.Menu;
import org.debugroom.wedding.domain.model.entity.User;

import org.debugroom.wedding.domain.service.common.UserSharedService;
import org.debugroom.wedding.domain.repository.common.MenuRepository;

@Service("menuService")
public class MenuServiceImpl implements MenuService{
	
	@Inject
	UserSharedService userSharedService;
	
	@Inject
	MenuRepository menuRepository;
	
	public List<Menu> getUsableMenu(User user) throws BusinessException{
		// Check existing User.
		try {
			userSharedService.exists(user);
		} catch (BusinessException e) {
			throw new BusinessException("userSharedService.error.0002");
		}
		List<Menu> menuList = menuRepository
				.findByAuthorityLevelLessThanOrderByMenuIdAsc(
						user.getAuthorityLevel());
		return menuList;
	}
}
