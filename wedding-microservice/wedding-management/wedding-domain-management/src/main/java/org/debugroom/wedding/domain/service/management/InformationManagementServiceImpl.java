package org.debugroom.wedding.domain.service.management;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.entity.Information;
import org.debugroom.wedding.domain.entity.Notification;
import org.debugroom.wedding.domain.entity.NotificationPK;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.model.common.UpdateResult;
import org.debugroom.wedding.domain.model.management.InformationDetail;
import org.debugroom.wedding.domain.model.management.InformationDraft;
import org.debugroom.wedding.domain.repository.jpa.InformationRepository;
import org.debugroom.wedding.domain.repository.jpa.NotificationRepository;
import org.debugroom.wedding.domain.repository.jpa.spec.FindNotViewInformationUsersByInfoId;
import org.debugroom.wedding.domain.repository.jpa.spec.FindUsersByInfoIdAndIsAccessed;
import org.debugroom.wedding.domain.repository.jpa.UserRepository;
import org.debugroom.wedding.domain.service.common.DateUtil;
import org.debugroom.wedding.domain.service.common.UserSharedService;

@Transactional
@Service("informationManagementService")
public class InformationManagementServiceImpl implements InformationManagementService{

	@Inject
	InformationRepository informationRepository;
	
	@Inject
	UserRepository userRepository;
	
	@Inject
	NotificationRepository notificationRepository;
	
	@Inject
	UserSharedService userSharedService;
	
	@Inject
	Mapper mapper;

	@Inject
	ManagementDomainProperties properties;
	
	@Override
	public Page<Information> getInformationUsingPage(Pageable pageable) {
		return informationRepository.findAll(pageable);
	}

	@Override
	public InformationDetail getInformationDetail(String infoId) {
		Information information = informationRepository.findOne(infoId);
		List<User> accessedUsers = 
				userRepository.findAll(FindUsersByInfoIdAndIsAccessed.builder()
				.infoId(infoId).isAccessed(true).build());
		List<User> noAccessedUsers = 
				userRepository.findAll(FindUsersByInfoIdAndIsAccessed.builder()
				.infoId(infoId).isAccessed(false).build());
		return InformationDetail.builder()
				.information(information)
				.accessedUsers(accessedUsers)
				.noAccessedUsers(noAccessedUsers)
				.build();
	}

	@Override
	public UpdateResult<InformationDetail> updateInformation(
			InformationDraft informationDraft) throws BusinessException {
		
		Information information = informationDraft.getInformation();
		
		UpdateResult<InformationDetail> updateResult = new UpdateResult<InformationDetail>();
		
		InformationDetail updateTargetInformationDetail = getInformationDetail(information.getInfoId());
		Information updateTargetInformation = updateTargetInformationDetail.getInformation();
//		InformationDetail beforeUpdate = mapper.map(updateTargetInformationDetail, InformationDetail.class,
//				"informationDetailMappingExcludingUserRelatedEntity");
		
		InformationDetail beforeUpdate = InformationDetail.builder()
				.information(Information.builder()
						.infoId(updateTargetInformation.getInfoId())
						.infoPagePath(updateTargetInformation.getInfoPagePath())
						.registratedDate(updateTargetInformation.getRegistratedDate())
						.releaseDate(updateTargetInformation.getReleaseDate())
						.title(updateTargetInformation.getTitle())
						.ver(updateTargetInformation.getVer())
						.build())
				.accessedUsers(new ArrayList<User>(updateTargetInformationDetail.getAccessedUsers()))
				.noAccessedUsers(new ArrayList<User>(updateTargetInformationDetail.getNoAccessedUsers()))
				.build();

		boolean isChangedInformation = false;
		List<String> updateParamList = new ArrayList<String>();
		
		if(!updateTargetInformation.getTitle().equals(information.getTitle())){
			updateTargetInformation.setTitle(information.getTitle());
			updateTargetInformation.setLastUpdatedDate(DateUtil.getCurrentDate());
			updateParamList.add("information.title");
			isChangedInformation = true;
		}

		if(!updateTargetInformation.getReleaseDate().toString().equals(information.getReleaseDate().toString())){
			updateTargetInformation.setReleaseDate(information.getReleaseDate());
			updateTargetInformation.setLastUpdatedDate(DateUtil.getCurrentDate());
			updateParamList.add("information.releaseDate");
			isChangedInformation = true;
		}
		
		/*
		if(null != informationDraft.getMessageBody()){
			File file = new File(new StringBuilder()
					.append(rootPathForMessage)
					.append(java.io.File.separator)
					.append(information.getInfoPagePath())
					.toString());
			try{
				FileUtils.writeStringToFile(file, informationDraft.getMessageBody(), "UTF-8");
			}catch (IOException e){
				throw new BusinessException(
						"informationManagementService.error.0001", e, information.getInfoId());
			}
			updateTargetInformation.setLastUpdatedDate(DateUtil.getCurrentDate());
			updateParamList.add("information.messageBody");
			isChangedInformation = true;
		}
		*/
		
		if(null != informationDraft.getExcludeUsers()){
			for(User excludeUser : informationDraft.getExcludeUsers()){
				if(null != excludeUser.getUserId()){
					Iterator<Notification> iterator = 
							updateTargetInformation.getNotifications().iterator();
					while(iterator.hasNext()){
						Notification notification = iterator.next();
						if(excludeUser.getUserId().equals(
								notification.getId().getUserId())){
							iterator.remove();
						}
					}
					updateParamList.add(new StringBuilder()
							.append("excludeUser-")
							.append(excludeUser.getUserId())
							.toString());
				}
			}
		}
		
		if(null != informationDraft.getViewUsers()){
			for(User viewUser : informationDraft.getViewUsers()){
				if(null != viewUser.getUserId()){
					updateTargetInformation.addNotification(
							Notification.builder()
							.id(NotificationPK.builder()
									.infoId(information.getInfoId())
									.userId(viewUser.getUserId())
									.build())
							.lastUpdatedDate(DateUtil.getCurrentDate())
							.isAccessed(false)
							.ver(0)
							.build());
					updateParamList.add(new StringBuilder()
							.append("viewUser-")
							.append(viewUser.getUserId())
							.toString());
				}
			}
			updateTargetInformationDetail.setNoAccessedUsers(userRepository.findAll());
		}
		
		if(isChangedInformation){
			notificationRepository.updateIsAccessedNyInfoId(false, information.getInfoId());
		}
		
		updateResult.setUpdateParamList(updateParamList);
		updateResult.setBeforeEntity(beforeUpdate);
		updateResult.setAfterEntity(updateTargetInformationDetail);
		return updateResult;
	}

	@Override
	public InformationDraft createInformationDraft(InformationDraft informationDraft)
			throws BusinessException {
		
		Information information = informationDraft.getInformation();
		String newInfoId = getNewInfoId();
		information.setInfoId(newInfoId);
		information.setRegistratedDate(DateUtil.getCurrentDate());
		return informationDraft;

	}

	@Override
	public Information saveInformation(InformationDraft informationDraft) throws BusinessException {
		
		Information information = informationDraft.getInformation();
		
		information.setLastUpdatedDate(DateUtil.getCurrentDate());
		
		Set<Notification> notifications = new HashSet<Notification>();
		
		for(User user : informationDraft.getViewUsers()){
			notifications.add(Notification.builder()
					.id(NotificationPK.builder()
							.userId(user.getUserId())
							.infoId(information.getInfoId())
							.build())
					.isAccessed(false)
					.lastUpdatedDate(DateUtil.getCurrentDate())
					.ver(0)
					.build());
		}
		
		information.setNotifications(notifications);
		return informationRepository.save(information);
	}

	@Override
	public List<User> getUsers() {
		return userSharedService.getUsers();
	}

	@Override
	public List<User> getNoViewers(String infoId) {
		FindNotViewInformationUsersByInfoId spec = FindNotViewInformationUsersByInfoId
				.builder().infoId(infoId).build();
		return userRepository.findAll(spec);
	}
	
	private String getNewInfoId(){
		Information information = informationRepository.findTopByOrderByInfoIdDesc();
		StringBuilder stringBuilder = new StringBuilder().append("00000000");
		if(null != information){
			if(!"00000000".equals(information.getInfoId())){
				stringBuilder.append(Integer.parseInt(StringUtils.stripStart(
						information.getInfoId(), "0"))+1);
			}else{
				stringBuilder.append("1");
			}
		}
		String sequence = stringBuilder.toString();
		return StringUtils.substring(
				sequence, sequence.length()-8, sequence.length());
	}

	@Override
	public Information deleteInformation(String infoId) {
		Information deleteInformation = informationRepository.findOne(infoId);
		informationRepository.delete(deleteInformation);
		return deleteInformation;
	}
	
}
