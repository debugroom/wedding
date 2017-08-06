package org.debugroom.wedding.domain.profile.service;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.common.model.UpdateResult;
import org.debugroom.wedding.domain.model.entity.User;

public interface ProfileManagementService {

	public User getUserProfile(String userId) throws BusinessException;

	public UpdateResult<User> updateUserProfile(User user) throws BusinessException;

}
