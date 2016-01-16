package org.debugroom.wedding.domain.service.profile;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.model.entity.User;

public interface ProfileManagementService {

	public User getUserProfile(String userId) throws BusinessException;

}
