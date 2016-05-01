package org.debugroom.wedding.domain.service.management;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.common.DomainProperties;
import org.debugroom.wedding.domain.model.entity.Infomation;
import org.debugroom.wedding.domain.model.entity.Notification;
import org.debugroom.wedding.domain.model.entity.NotificationPK;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.repository.basic.FindNotViewInfomationUsersByInfoId;
import org.debugroom.wedding.domain.repository.basic.InfomationRepository;
import org.debugroom.wedding.domain.repository.basic.NotificationRepository;
import org.debugroom.wedding.domain.repository.common.UserRepository;
import org.debugroom.wedding.domain.service.common.UpdateResult;
import org.debugroom.wedding.domain.service.common.UserSharedService;

@Transactional
@Service("infomationManagementService")
public class InfomationManagementServiceImpl implements InfomationManagementService{

	@Inject
	InfomationRepository infomationRepository;
	
	@Inject
	UserRepository userRepository;
	
	@Inject
	NotificationRepository notificationRepository;
	
	@Inject
	UserSharedService userSharedService;
	
	@Inject
	DomainProperties domainProperties;
	
	@Inject
	Mapper mapper;
	
	@Override
	public Page<Infomation> getInfomationUsingPage(Pageable pageable) {
		return infomationRepository.findAll(pageable);
	}

	@Override
	public InfomationDetail getInfomationDetail(String infoId) {
		Infomation infomation = infomationRepository.findOne(infoId);
		List<User> accessedUsers = userRepository
				.findUsersByInfoIdAndIsAccessed(infoId, true);
		List<User> noAccessedUsers = userRepository
				.findUsersByInfoIdAndIsAccessed(infoId, false);
		return InfomationDetail.builder()
					.infomation(infomation)
					.accessedUsers(accessedUsers)
					.noAccessedUsers(noAccessedUsers)
					.build();
	}

	@Override
	public UpdateResult<InfomationDetail> updateInfomation(
		InfomationDraft infomationDraft, String rootPathForMessage) throws BusinessException {
		
		Infomation infomation = infomationDraft.getInfomation();
		
		UpdateResult<InfomationDetail> updateResult = new UpdateResult<InfomationDetail>();
		
		InfomationDetail updateTargetInfomationDetail = getInfomationDetail(infomation.getInfoId());
		Infomation updateTargetInfomation = updateTargetInfomationDetail.getInfomation();
		InfomationDetail beforeUpdate = mapper.map(updateTargetInfomationDetail, InfomationDetail.class, 
				"infomationDetailMappingExcludingUserRelatedEntity");

		boolean isChangedInfomation = false;
		List<String> updateParamList = new ArrayList<String>();
		
		if(!updateTargetInfomation.getTitle().equals(infomation.getTitle())){
			updateTargetInfomation.setTitle(infomation.getTitle());
			updateTargetInfomation.setLastUpdatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			updateParamList.add("infomation.title");
			isChangedInfomation = true;
		}
		
		if(!updateTargetInfomation.getReleaseDate().toString().equals(infomation.getReleaseDate().toString())){
			updateTargetInfomation.setReleaseDate(infomation.getReleaseDate());
			updateTargetInfomation.setLastUpdatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			updateParamList.add("infomation.releaseDate");
			isChangedInfomation = true;
		}
		
		if(null != infomationDraft.getMessageBody()){
			File file = new File(new StringBuilder()
										.append(rootPathForMessage)
										.append(domainProperties.getInfoRootPath())
										.append(infomation.getInfoPagePath())
										.toString());
			try {
				FileUtils.writeStringToFile(file, infomationDraft.getMessageBody(), "UTF-8");
			} catch (IOException e) {
				throw new BusinessException(
						"infomationManagementService.error.0001", e, infomation.getInfoId());
			}
			updateTargetInfomation.setLastUpdatedDate(
					new Timestamp(Calendar.getInstance().getTimeInMillis()));
			updateParamList.add("infomation.messageBody");
			isChangedInfomation = true;
		}
			
		if(null != infomationDraft.getExcludeUsers()){
			for(User excludeUser : infomationDraft.getExcludeUsers()){
				if(null != excludeUser.getUserId()){
					/*
					Notification notification = notificationRepository.findOne(
							NotificationPK.builder()
											.infoId(infomation.getInfoId())
											.userId(excludeUser.getUserId())
											.build()
							);
					notificationRepository.delete(notification);
					 */
					Iterator<Notification> iterator = updateTargetInfomation.getNotifications().iterator();
					while(iterator.hasNext()){
						Notification notification = iterator.next();
						if(excludeUser.getUserId().equals(notification.getId().getUserId())){
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

		if(null != infomationDraft.getViewUsers()){
			for(User viewUser : infomationDraft.getViewUsers()){
				if(null != viewUser.getUserId()){
					updateTargetInfomation.addNotification(Notification.builder()
															.id(NotificationPK
																	.builder()
																	.infoId(infomation.getInfoId())
																	.userId(viewUser.getUserId())
																	.build())
															.lastUpdatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()))
															.ver(0)
															.build());
					updateParamList.add(new StringBuilder()
												.append("viewUser-")
												.append(viewUser.getUserId())
												.toString());
				}
			}
			updateTargetInfomationDetail.setNoAccessedUsers(userRepository.findAll());
		}


		if(isChangedInfomation){
			notificationRepository.updateIsAccessedByInfoId(false, infomation.getInfoId());
		}
		
		updateResult.setUpdateParamList(updateParamList);
		updateResult.setBeforeEntity(beforeUpdate);
		updateResult.setAfterEntity(updateTargetInfomationDetail);

		return updateResult;

	}

	@Override
	public InfomationDraft createInfomationDraft(InfomationDraft infomationDraft,
			String rootPathForMessage) throws BusinessException {

		String sequence = new StringBuilder()
								.append("00000000")
								.append(infomationRepository.count())
								.toString();
		String newInfoId = StringUtils.substring(sequence,
				sequence.length()-8, sequence.length());
		Infomation infomation = infomationDraft.getInfomation();
		infomation.setInfoId(newInfoId);
		infomation.setRegistratedDate(new Timestamp(
				Calendar.getInstance().getTimeInMillis()));
		
		String infoPagePath = new StringBuilder()
									.append("info/")
									.append(newInfoId)
									.append("_")
									.append(infomationDraft.getInfoName())
									.append(".jsp")
									.toString();

		File file = new File(new StringBuilder()
									.append(rootPathForMessage)
									.append(domainProperties.getInfoRootPath())
									.append(infoPagePath)
									.toString());

		try {
			FileUtils.writeStringToFile(file, infomationDraft.getMessageBody(), "UTF-8");
		} catch (IOException e) {
			throw new BusinessException(
					"infomationManagementService.error.0002", e, "");
		}
		infomation.setInfoPagePath(infoPagePath);
		
		return infomationDraft;
	}

	@Override
	public Infomation saveInfomation(InfomationDraft infomationDraft) throws BusinessException {

		Infomation infomation = infomationDraft.getInfomation();
		
		infomation.setLastUpdatedDate(new Timestamp(
				Calendar.getInstance().getTimeInMillis()));
		
		try{

			Set<Notification> notifications = new HashSet<Notification>();

			for(User user : infomationDraft.getViewUsers()){
				notifications.add(Notification.builder()
									.id(NotificationPK.builder()
												.userId(user.getUserId())
												.infoId(infomation.getInfoId())
												.build())
									.isAccessed(false)
									.lastUpdatedDate(new Timestamp(
											Calendar.getInstance().getTimeInMillis()))
									.ver(0)
									.build());
			}

			infomation.setNotifications(notifications);
			return infomationRepository.save(infomation);

		}catch(DataIntegrityViolationException e){
			throw new BusinessException(
					"infomationManagementService.error.0003", e, infomation.getInfoId());
		}catch(OptimisticEntityLockException e){
			throw new BusinessException(
					"infomationManagementService.error.0004", e, infomation.getInfoId());
		}
	}

	@Override
	public List<User> getUsers() {
		return userSharedService.getUsers();
	}

	@Override
	public List<User> getNoViewers(String infoId) {
		FindNotViewInfomationUsersByInfoId spec = FindNotViewInfomationUsersByInfoId
														.builder()
														.infoId(infoId)
														.build();
		return userRepository.findAll(spec);
	}

	@Override
	public Infomation deleteInfomation(String infoId) {
		Infomation deleteInfomation = infomationRepository.findOne(infoId);
		infomationRepository.delete(deleteInfomation);
		return deleteInfomation;
	}

}
