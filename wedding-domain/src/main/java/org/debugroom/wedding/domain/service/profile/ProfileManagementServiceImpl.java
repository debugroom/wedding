package org.debugroom.wedding.domain.service.profile;

import java.util.ArrayList;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.service.common.UserSharedService;
import org.debugroom.wedding.domain.service.common.UpdateResult;

@Transactional
@Service("profileManagementService")
public class ProfileManagementServiceImpl implements ProfileManagementService{

	@Inject
	UserSharedService userSharedService;
	
	@Override
	public User getUserProfile(String userId) throws BusinessException {
		return userSharedService.getUser(userId);
	}

	@Override
	public UpdateResult<User> updateUserProfile(User user) throws BusinessException {
		UpdateResult<User> updateResult = null;
		if(userSharedService.exists(user)){
			updateResult = userSharedService.updateUser(user);
		}else{
			throw new BusinessException("profileManagementService.error.0001");
		}
		return updateResult;
	}

	
}
