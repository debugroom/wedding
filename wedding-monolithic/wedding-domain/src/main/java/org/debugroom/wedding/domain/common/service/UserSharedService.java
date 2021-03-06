package org.debugroom.wedding.domain.common.service;

import java.util.List;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.common.model.UpdateResult;
import org.debugroom.wedding.domain.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserSharedService {

	public boolean exists(User user) throws BusinessException;

	public boolean exists(String loginId);
	
	public User getUser(String userId) throws BusinessException;
	
	public UpdateResult<User> updateUser(User user) throws BusinessException;
	
	public List<User> getUsers();
	
	public Page<User> getUsersUsingPageable(Pageable pageable);

	public String getNewUserId();
	
	public User saveUser(User user) throws BusinessException;
	
	public User deleteUser(User user);
	
	
}
