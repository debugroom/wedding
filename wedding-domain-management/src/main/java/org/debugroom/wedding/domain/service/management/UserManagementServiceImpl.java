package org.debugroom.wedding.domain.service.management;

import java.util.List;

import javax.inject.Inject;

import org.dozer.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.entity.Credential;
import org.debugroom.wedding.domain.entity.Email;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.model.common.UpdateResult;
import org.debugroom.wedding.domain.service.common.CommonDomainProperties;
import org.debugroom.wedding.domain.service.common.DateUtil;
import org.debugroom.wedding.domain.service.common.UserSharedService;

@Service("userManagementService")
public class UserManagementServiceImpl implements UserManagementService{

	@Inject
	Mapper mapper;
	
	@Inject
	UserSharedService userSharedService;
	
	@Inject
	CommonDomainProperties properties;
	
	@Override
	public List<User> getUsers() {
		return userSharedService.getUsers();
	}

	@Override
	public User getUserProfile(String userId) throws BusinessException {
		return userSharedService.getUser(userId);
	}

	@Override
	public Page<User> getUsersUsingPage(Pageable pageable) {
		return userSharedService.getUsersUsingPageable(pageable);
	}

	@Override
	public UpdateResult<User> udpateUser(User user) throws BusinessException {
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
					&& properties.getCredentialTypePasswordLogicalName().equals(
							credential.getId().getCredentialType())){
				credential.getId().setUserId(newUserId);
			}
		}
		return user;
	}

	@Override
	public User saveUser(User user) throws BusinessException {
		user.setLastUpdatedDate(DateUtil.getCurrentDate());
		
		user.getAddress().setUserId(user.getUserId());
		user.getAddress().setLastUpdatedDate(DateUtil.getCurrentDate());
		
		int count = 0;
		
		for(Credential credential : user.getCredentials()){
			credential.getId().setUserId(user.getUserId());
			credential.setLastUpdatedDate(DateUtil.getCurrentDate());
			credential.setValidDate(DateUtil.getFutureDateFromNow(
					properties.getPasswordExpiredDayDefault()));
			if(properties.getCredentialTypePasswordLogicalName().equals(
					credential.getId().getCredentialType())){
				count++;
				if(1 < count){
					user.getCredentials().remove(credential);
				}
			}
		}
		
		for(Email email : user.getEmails()){
			email.getId().setUserId(user.getUserId());
			email.setLastUpdatedDate(DateUtil.getCurrentDate());
		}

		return userSharedService.saveUser(user);
	}

	@Override
	public User deleteUser(String userId) {
		return userSharedService.deleteUser(
				User.builder().userId(userId).build());
	}

	@Override
	public boolean existsUser(String loginId) {
		return userSharedService.exists(loginId);
	}

	@Override
	public User getUser(String loginId) throws BusinessException {
		return userSharedService.getUserByLoginId(loginId);
	}

}
