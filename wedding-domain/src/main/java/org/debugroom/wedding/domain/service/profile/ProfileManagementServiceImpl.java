package org.debugroom.wedding.domain.service.profile;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.service.common.UserSharedService;

@Service("profileManagementService")
public class ProfileManagementServiceImpl implements ProfileManagementService{

	@Inject
	UserSharedService userSharedService;
	
	@Override
	public User getUserProfile(String userId) throws BusinessException {
		return userSharedService.getUser(userId);
	}

	
}
