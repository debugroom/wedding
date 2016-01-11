package org.debugroom.wedding.domain.service.common;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.model.entity.User;

public interface UserSharedService {

	public boolean exists(User user) throws BusinessException;
	
	public User getUser(String userId) throws BusinessException;
	
}
