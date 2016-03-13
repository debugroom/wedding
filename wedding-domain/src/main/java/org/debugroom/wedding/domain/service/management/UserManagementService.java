package org.debugroom.wedding.domain.service.management;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.service.common.UpdateResult;

public interface UserManagementService {

	public List<User> getUsers();
	
	public User getUserProfile(String userId) throws BusinessException;
	
	public Page<User> getUsersUsingPage(Pageable pageable);

	public UpdateResult<User> updateUser(User user) throws BusinessException;

	public User createUserProfile(User user) throws BusinessException;
	
	public User saveUser(User user) throws BusinessException;
	
	public User deleteUser(String userId);

	public boolean existsUser(String loginId);

}
