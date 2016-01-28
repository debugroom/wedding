package org.debugroom.wedding.domain.service.management;

import java.util.List;

import org.debugroom.framework.common.exception.BusinessException;

import org.debugroom.wedding.domain.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserManagementService {

	public List<User> getUsers();
	
	public User getUserProfile(String userId) throws BusinessException;
	
	public Page<User> getUsersUsingPage(Pageable pageable);

}
