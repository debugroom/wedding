package org.debugroom.wedding.domain.service.common;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import org.debugroom.wedding.domain.repository.common.UserRepository;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.model.entity.User;

@Service("userSharedService")
public class UserSharedServiceImpl implements UserSharedService{

	@Inject
	UserRepository userRepository;
	
	@Override
	public boolean exists(User user) throws BusinessException{
		long count = userRepository.countByUserId(user.getUserId());
		if(0 == count){
			throw new BusinessException("userSharedService.error.0001");
		}
		return true;
	}

	@Override
	public User getUser(String userId) throws BusinessException {
		User user = userRepository.findOne(userId);
		if(null == user){
			throw new BusinessException("userSharedService.error.0001");
		}
		return user;
	}

}
