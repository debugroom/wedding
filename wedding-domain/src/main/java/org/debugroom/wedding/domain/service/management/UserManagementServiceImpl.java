package org.debugroom.wedding.domain.service.management;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.service.common.UserSharedService;

@Service("userManagementService")
public class UserManagementServiceImpl implements UserManagementService {

	@Inject
	UserSharedService userSharedService;
	
	@Override
	public User getUserProfile(String userId) throws BusinessException {
		return userSharedService.getUser(userId);
	}

	@Override
	public List<User> getUsers() {
		return userSharedService.getUsers();
	}

	@Override
	public Page<User> getUsersUsingPage(Pageable pageable) {
		return userSharedService.getUsersUsingPageable(pageable);
	}

	
}
