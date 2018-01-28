package org.debugroom.wedding.domain.service.profile;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.model.common.UpdateResult;
import org.debugroom.wedding.domain.service.common.UserSharedService;

@Transactional
@Service("profileService")
public class ProfileServiceImpl implements ProfileService{

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
			throw new BusinessException("profileService.error.0001");
		}
		return updateResult;
	}

}
