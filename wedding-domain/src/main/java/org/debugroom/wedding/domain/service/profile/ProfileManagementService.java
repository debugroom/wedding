package org.debugroom.wedding.domain.service.profile;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.service.common.UpdateResult;

public interface ProfileManagementService {

	public User getUserProfile(String userId) throws BusinessException;

	public UpdateResult updateUserProfile(User user) throws BusinessException;

}
