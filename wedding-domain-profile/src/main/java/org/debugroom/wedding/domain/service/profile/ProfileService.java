package org.debugroom.wedding.domain.service.profile;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.model.common.UpdateResult;
import org.debugroom.wedding.domain.entity.User;

public interface ProfileService {

	public User getUserProfile(String userId) throws BusinessException;
	
	public UpdateResult<User> updateUserProfile(User user) throws BusinessException;
	
}
