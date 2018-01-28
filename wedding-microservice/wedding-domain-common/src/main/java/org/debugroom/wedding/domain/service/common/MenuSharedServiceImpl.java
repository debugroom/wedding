package org.debugroom.wedding.domain.service.common;

import java.util.List;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.entity.Menu;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.repository.jpa.MenuRepository;

@Service("menuSharedService")
public class MenuSharedServiceImpl implements MenuSharedService{

	@Inject
	UserSharedService userSharedService;
	
	@Inject
	MenuRepository menuRepository;
	
	@Override
	public List<Menu> getUsableMenu(User user) throws BusinessException {
		// Check existing User.
		User targetUser = null;
		try {
			targetUser = userSharedService.getUser(user.getUserId());
		} catch (BusinessException e) {
			throw new BusinessException("userSharedService.error.0002");
		}
		List<Menu> menuList = menuRepository
				.findByAuthorityLevelLessThanOrderByMenuIdAsc(targetUser.getAuthorityLevel());
		return menuList;
	}

}
