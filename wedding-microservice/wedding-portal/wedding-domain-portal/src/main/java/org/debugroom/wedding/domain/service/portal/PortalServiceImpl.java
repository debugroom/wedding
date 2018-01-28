package org.debugroom.wedding.domain.service.portal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.entity.Information;
import org.debugroom.wedding.domain.entity.Notification;
import org.debugroom.wedding.domain.entity.NotificationPK;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.entity.portal.Request;
import org.debugroom.wedding.domain.entity.portal.RequestStatus;
import org.debugroom.wedding.domain.entity.portal.RequestStatusPK;
import org.debugroom.wedding.domain.model.portal.PortalInfoOutput;
import org.debugroom.wedding.domain.repository.jpa.InformationRepository;
import org.debugroom.wedding.domain.repository.jpa.NotificationRepository;
import org.debugroom.wedding.domain.repository.jpa.portal.RequestRepository;
import org.debugroom.wedding.domain.repository.jpa.portal.RequestStatusRepository;
import org.debugroom.wedding.domain.service.common.DateUtil;
import org.debugroom.wedding.domain.service.common.UserSharedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("portalService")
public class PortalServiceImpl implements PortalService{

	@Inject
	UserSharedService userSharedService;
	
	@Inject
	InformationRepository informationRepository;
	
	@Inject
	RequestRepository requestRepository;
	
	@Inject
	RequestStatusRepository requestStatusRepository;
	
	@Inject
	NotificationRepository notificationRepository;
	
	@Override
	public PortalInfoOutput getPortalInfo(User user)  {
		PortalInfoOutput output = PortalInfoOutput.builder().build();
		try {
			output.setUser(userSharedService.getUser(user.getUserId()));
			List<Information> informationList = informationRepository
					.findUserByUserIdAndReleaseDateLessThan(
							user.getUserId(), DateUtil.getCurrentDate());
			for(Information information : informationList){
				for(Notification notification : information.getNotifications()){
					if(notification.getIsAccessed()){
						output.setUnWatched(true);
					}
				}
			}
			output.setInformationList(informationList);
			List<Request> requestList = new ArrayList<Request>();
			for(RequestStatus requestStatus : 
				requestStatusRepository.findByIdUserId(user.getUserId())){
				requestList.add(requestStatus.getRequest());
				if(!requestStatus.getIsAnswered()){
					output.setNotAnswered(true);
				}
			}
			output.setRequestList(requestList);
		} catch (BusinessException e) {
			new BusinessException("portalService.error.0001");
		}
		return output;
	}

	@Override
	public Information getInformation(String infoId) {
		return informationRepository.findOne(infoId);
	}

	@Override
	public Request getRequest(String requestId) {
		return requestRepository.findOne(requestId);
	}

	@Override
	public User getUser(String userId) throws BusinessException {
		return userSharedService.getUser(userId);
	}

	@Override
	public boolean updateRequestStatus(String userId, String requestId, boolean isApproved) {
		RequestStatus requestStatus = requestStatusRepository.findOne(
				RequestStatusPK.builder()
				.requestId(requestId)
				.userId(userId)
				.build());
		requestStatus.setIsAnswered(true);
		requestStatus.setIsApproved(isApproved);
		requestStatus.setLastUpdatedDate(DateUtil.getCurrentDate());
		return isApproved;
	}

	@Override
	public boolean updateNotification(String infoId, String userId, boolean isWatched) {
		Notification notification = notificationRepository.findOne(
				NotificationPK.builder()
				.infoId(infoId)
				.userId(userId)
				.build());
		notification.setIsAccessed(isWatched);
		notification.setLastUpdatedDate(DateUtil.getCurrentDate());
		return true;
	}

}
