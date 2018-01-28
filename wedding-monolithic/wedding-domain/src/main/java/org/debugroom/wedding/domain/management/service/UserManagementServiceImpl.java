package org.debugroom.wedding.domain.management.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.dozer.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.common.DomainProperties;
import org.debugroom.wedding.domain.common.model.UpdateResult;
import org.debugroom.wedding.domain.common.service.UserSharedService;
import org.debugroom.wedding.domain.model.entity.Credential;
import org.debugroom.wedding.domain.model.entity.Email;
import org.debugroom.wedding.domain.model.entity.User;

@Service("userManagementService")
public class UserManagementServiceImpl implements UserManagementService {

	@Inject
	Mapper mapper;
	
	@Inject
	DomainProperties domainPorperties;
	
	@Inject
	UserSharedService userSharedService;
	
	@Override
	public User getUserProfile(String userId) throws BusinessException {
		return userSharedService.getUser(userId);
	}

	@Override
	public List<User> getUsers() {
		return userSharedService.getUsers();
	}

	@Override
	public Page<User> getUsersUsingPage(Pageable pageable) {
		return userSharedService.getUsersUsingPageable(pageable);
	}

	@Override
	public UpdateResult<User> updateUser(User user) throws BusinessException {
		UpdateResult<User> updateResult = null;
		if(userSharedService.exists(user)){
			updateResult = userSharedService.updateUser(user);
		}else{
			throw new BusinessException("userManagementService.error.0001");
		}
		return updateResult;
	}

	@Override
	public User createUserProfile(User user) throws BusinessException {
		if(userSharedService.exists(user.getLoginId())){
			throw new BusinessException("userManagementService.error.0002", 
					null, user.getLoginId());
		}
		String newUserId = userSharedService.getNewUserId();
		user.setUserId(newUserId);
		user.getAddress().setUserId(newUserId);
		for(Email email : user.getEmails()){
			if(null != email.getEmail()){
				email.getId().setUserId(newUserId);
			}
		}
		for(Credential credential : user.getCredentials()){
			if(null != credential.getCredentialKey() 
					&& domainPorperties.getCredentialTypePassword().equals(
							credential.getId().getCredentialType())){
				credential.getId().setUserId(newUserId);
			}
		}
		return user;
	}

	@Override
	public User saveUser(User user) throws BusinessException {

		user.setLastUpdatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));

		user.getAddress().setUserId(user.getUserId());
		user.getAddress().setLastUpdatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(
				domainPorperties.getPasswordExpiredDayDefault()));
		
		int count = 0;

		for(Credential credential : user.getCredentials()){

			credential.getId().setUserId(user.getUserId());;
			credential.setLastUpdatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			credential.setValidDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));

			if(domainPorperties.getCredentialTypePassword().equals(
					credential.getId().getCredentialType())){
				count++;
				if(1 < count){
					user.getCredentials().remove(credential);
				}
			}
		}

		for(Email email : user.getEmails()){
			email.getId().setUserId(user.getUserId());
			email.setLastUpdatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		}

		return userSharedService.saveUser(user);
	}

	@Override
	public boolean existsUser(String loginId){
		return userSharedService.exists(loginId);
	}

	@Override
	public User deleteUser(String userId) {
		return userSharedService.deleteUser(
				User.builder().userId(userId).build());
	}

	
}
