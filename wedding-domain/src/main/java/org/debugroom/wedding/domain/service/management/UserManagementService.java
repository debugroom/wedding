package org.debugroom.wedding.domain.service.management;

import org.debugroom.framework.common.exception.BusinessException;

import org.debugroom.wedding.domain.model.entity.User;

public interface UserManagementService {

	public User getUserProfile(String userId) throws BusinessException;

}