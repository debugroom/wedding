package org.debugroom.wedding.domain.service.common;

import java.util.List;
import java.util.Set;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.repository.jpa.NotificationRepository;
import org.debugroom.wedding.domain.repository.jpa.UserRepository;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.model.common.UpdateResult;
import org.debugroom.wedding.domain.entity.Address;
import org.debugroom.wedding.domain.entity.Credential;
import org.debugroom.wedding.domain.entity.CredentialPK;
import org.debugroom.wedding.domain.entity.Email;
import org.debugroom.wedding.domain.entity.EmailPK;
import org.debugroom.wedding.domain.entity.Notification;

@Transactional
@Service("userSharedService")
public class UserSharedServiceImpl implements UserSharedService{

	@Inject
	Mapper mapper;
	
	@Inject
	PasswordEncoder passwordEncoder;
	
	@Inject
	CommonDomainProperties domainProperties;
	
	@Inject
	UserRepository userRepository;
	
	@Inject
	NotificationRepository notificationRepository;
	
	@Override
	public boolean exists(User user) throws BusinessException{
		long count = userRepository.countByUserId(user.getUserId());
		if(0 == count){
			throw new BusinessException("userSharedService.error.0001");
		}
		return true;
	}

	@Override
	public boolean exists(String loginId){
		long count = userRepository.countByloginId(loginId);
		if(0 == count){
			return false;
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

	@Override
	public UpdateResult<User> updateUser(User user) throws BusinessException {

		UpdateResult<User> updateResult = new UpdateResult<User>();

		User updateTargetUser = getUser(user.getUserId());
		Set<Email> beforeEmails = new HashSet<Email>();
		Set<Credential> beforeCredentials = new HashSet<Credential>();
		for(Email email : updateTargetUser.getEmails()){
			beforeEmails.add(Email.builder()
					.id(EmailPK.builder()
							.userId(email.getId().getUserId())
							.emailId(email.getId().getEmailId())
							.build())
					.email(email.getEmail())
					.build());
		}
		for(Credential credential : updateTargetUser.getCredentials()){
			beforeCredentials.add(Credential.builder()
					.id(CredentialPK.builder()
							.userId(credential.getId().getUserId())
							.credentialType(credential.getId().getCredentialType())
							.build())
					.credentialKey(credential.getCredentialKey())
					.build());
		}

		User beforeUpdate = User.builder()
				.userId(updateTargetUser.getUserId())
				.firstName(updateTargetUser.getFirstName())
				.lastName(updateTargetUser.getLastName())
				.loginId(updateTargetUser.getLoginId())
				.authorityLevel(updateTargetUser.getAuthorityLevel())
				.imageFilePath(updateTargetUser.getImageFilePath())
				.isBrideSide(updateTargetUser.isBrideSide())
				.lastLoginDate(updateTargetUser.getLastLoginDate())
				.lastUpdatedDate(updateTargetUser.getLastUpdatedDate())
				.ver(updateTargetUser.getVer())
				.address(Address.builder()
						.userId(updateTargetUser.getAddress().getUserId())
						.postCd(updateTargetUser.getAddress().getPostCd())
						.address(updateTargetUser.getAddress().getAddress())
						.build())
				.credentials(beforeCredentials)
				.emails(beforeEmails)
				.build();

		List<String> updateParamList = new ArrayList<String>();
		
		if(!updateTargetUser.getFirstName().equals(user.getFirstName())){
			updateTargetUser.setFirstName(user.getFirstName());
			updateTargetUser.setLastUpdatedDate(DateUtil.getCurrentDate());
			updateParamList.add("firstName");
		}
		
		if(!updateTargetUser.getLastName().equals(user.getLastName())){
			updateTargetUser.setLastName(user.getLastName());
			updateTargetUser.setLastUpdatedDate(DateUtil.getCurrentDate());
			updateParamList.add("lastName");
		}
		
		if(!updateTargetUser.getLoginId().equals(user.getLoginId())){
			updateTargetUser.setLoginId(user.getLoginId());
			updateTargetUser.setLastUpdatedDate(DateUtil.getCurrentDate());
			updateParamList.add("loginId");
		}

		if(!updateTargetUser.getImageFilePath().equals(user.getImageFilePath())){
			updateTargetUser.setImageFilePath(user.getImageFilePath());
			updateTargetUser.setLastUpdatedDate(DateUtil.getCurrentDate());
			updateParamList.add("imageFile");
		}

		if(null != user.getAuthorityLevel()){
			if(updateTargetUser.getAuthorityLevel().intValue() 
				!= user.getAuthorityLevel().intValue()){
				updateTargetUser.setAuthorityLevel(user.getAuthorityLevel());
				updateTargetUser.setLastUpdatedDate(DateUtil.getCurrentDate());
				updateParamList.add("authorityLevel");
			}
		}

		if(updateTargetUser.isBrideSide() != user.isBrideSide()){
			updateTargetUser.setBrideSide(user.isBrideSide());
			updateTargetUser.setLastUpdatedDate(DateUtil.getCurrentDate());
			updateParamList.add("isBrideSide");
		}

		if(!updateTargetUser.getAddress().getPostCd().equals(
				user.getAddress().getPostCd())){
			updateTargetUser.getAddress().setPostCd(user.getAddress().getPostCd());
			updateTargetUser.getAddress().setLastUpdatedDate(DateUtil.getCurrentDate());
			updateParamList.add("address.postCd");
		}
		
		if(!updateTargetUser.getAddress().getAddress().equals(
				user.getAddress().getAddress())){
			updateTargetUser.getAddress().setAddress(user.getAddress().getAddress());
			updateTargetUser.getAddress().setLastUpdatedDate(DateUtil.getCurrentDate());
			updateParamList.add("address.address");
		}
		
		if(null != user.getCredentials()){
			for(Credential credential : user.getCredentials()){
				// If credentialType equals "PASSWORD" and CredentialKey is not blank , null.
				if(domainProperties.getCredentialTypePasswordLogicalName().equals(
					credential.getId().getCredentialType()) 
					&& (!"".equals(credential.getCredentialKey()) 
							&& credential.getCredentialKey() != null)){
					for(Credential targetCredential : updateTargetUser.getCredentials()){
						// If update target credentialType equals "PASSWORD"
						if(domainProperties.getCredentialTypePasswordLogicalName().equals(
							targetCredential.getId().getCredentialType())){
						// Set encode password. 
							targetCredential.setCredentialKey(passwordEncoder.encode(
								credential.getCredentialKey()));
							targetCredential.setLastUpdatedDate(DateUtil.getCurrentDate());
							updateParamList.add(new StringBuilder()
													.append("credentials#")
													.append(targetCredential.getId().getCredentialType())
													.toString());
						}
					}
				}
			}
		}

		for(Email paramEmail : user.getEmails()){
			for(Email targetEmail : updateTargetUser.getEmails()){
				if(paramEmail.getId().getEmailId() == targetEmail.getId().getEmailId()
						&& !paramEmail.getEmail().equals(targetEmail.getEmail())){
					targetEmail.setEmail(paramEmail.getEmail());
					targetEmail.setLastUpdatedDate(DateUtil.getCurrentDate());
					updateParamList.add(new StringBuilder()
												.append("emails#")
												.append(targetEmail.getId().getEmailId())
												.toString());
				}
			}
		}

		updateResult.setUpdateParamList(updateParamList);
		updateResult.setBeforeEntity(beforeUpdate);
		updateResult.setAfterEntity(updateTargetUser);
		
		return updateResult;
		
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public Page<User> getUsersUsingPageable(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public String getNewUserId() {
		User user = userRepository.findTopByOrderByUserIdDesc();
		StringBuilder stringBuilder = new StringBuilder().append("00000000");
		if(null != user){
			if(!"00000000".equals(user.getUserId())){
				stringBuilder.append(Integer.parseInt(
					StringUtils.stripStart(user.getUserId(), "0"))+1);
			}else{
				stringBuilder.append("1");
			}
		}
		String sequence = stringBuilder.toString();
		return StringUtils.substring(sequence,
				sequence.length()-8, sequence.length());
	}

	@Override
	public User saveUser(User user) throws BusinessException {
		for(Credential credential : user.getCredentials()){
			if(domainProperties.getCredentialTypePasswordLogicalName()
					.equals(credential.getId().getCredentialType())){
				credential.setCredentialKey(passwordEncoder
						.encode(credential.getCredentialKey()));
			}
		}
		try{
			return userRepository.save(user);
		}catch(DataIntegrityViolationException e){
			throw new BusinessException(
					"userSharedService.error.0002", e, user.getUserId());
		}catch(OptimisticLockingFailureException e){
			throw new BusinessException(
					"userSharedService.error.0003", e, user.getUserId());
		}
	}

	@Override
	public User deleteUser(User user) {
		List<Notification> notifications = notificationRepository.findByIdUserId(user.getUserId());
		if(notifications != null && notifications.size() != 0){
			notificationRepository.delete(notifications);
		}
		User deleteUser = userRepository.findOne(user.getUserId());
		userRepository.delete(deleteUser);
		return deleteUser;
	}

	@Override
	public User getUserByLoginId(String loginId) throws BusinessException {
		User user = userRepository.findOneByLoginId(loginId);
		if(null == user){
			throw new BusinessException("userSharedService.error.0004", loginId);
		}
		return user;
	}

	@Override
	public User updateLastLoginDate(String userId) {
		User updateTargetUser = userRepository.findOne(userId);
		updateTargetUser.setLastLoginDate(DateUtil.getCurrentDate());
		return updateTargetUser;
	}

}
