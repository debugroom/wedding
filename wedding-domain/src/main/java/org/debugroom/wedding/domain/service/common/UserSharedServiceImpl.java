package org.debugroom.wedding.domain.service.common;

import java.util.List;
import java.util.ArrayList;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.debugroom.wedding.domain.repository.common.UserRepository;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.model.entity.Email;
import org.debugroom.wedding.domain.service.common.UpdateResult;

@Transactional
@Service("userSharedService")
public class UserSharedServiceImpl implements UserSharedService{

	@Inject
	Mapper mapper;
	
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

	@Override
	public UpdateResult<User> updateUser(User user) throws BusinessException {

		UpdateResult<User> updateResult = new UpdateResult<User>();

		User updateTargetUser = getUser(user.getUserId());
		User beforeUpdate = mapper.map(updateTargetUser, User.class);

		List<String> updateParamList = new ArrayList<String>();
		
		if(!updateTargetUser.getUserName().equals(user.getUserName())){
			updateTargetUser.setUserName(user.getUserName());
			updateParamList.add("userName");
		}
		
		if(!updateTargetUser.getLoginId().equals(user.getLoginId())){
			updateTargetUser.setLoginId(user.getLoginId());
			updateParamList.add("loginId");
		}

		if(!updateTargetUser.getImageFilePath().equals(user.getImageFilePath())){
			updateTargetUser.setImageFilePath(user.getImageFilePath());
			updateParamList.add("imageFile");
		}

		if(!updateTargetUser.getAddress().getPostCd().equals(
				user.getAddress().getPostCd())){
			updateTargetUser.getAddress().setPostCd(user.getAddress().getPostCd());
			updateParamList.add("address.postCd");
		}
		
		if(!updateTargetUser.getAddress().getAddress().equals(
				user.getAddress().getAddress())){
			updateTargetUser.getAddress().setAddress(user.getAddress().getAddress());
			updateParamList.add("address.address");
		}

		for(Email paramEmail : user.getEmails()){
			for(Email targetEmail : updateTargetUser.getEmails()){
				if(paramEmail.getId().getEmailId() == targetEmail.getId().getEmailId()
						&& !paramEmail.getEmail().equals(targetEmail.getEmail())){
					targetEmail.setEmail(paramEmail.getEmail());
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

}
