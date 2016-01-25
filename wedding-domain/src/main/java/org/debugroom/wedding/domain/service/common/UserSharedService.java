package org.debugroom.wedding.domain.service.common;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.service.common.UpdateResult;

public interface UserSharedService {

	public boolean exists(User user) throws BusinessException;
	
	public User getUser(String userId) throws BusinessException;
	
	public UpdateResult<User> updateUser(User user) throws BusinessException;
	
}
