package org.debugroom.wedding.domain.service.management;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.debugroom.wedding.domain.model.entity.Request;
import org.debugroom.wedding.domain.model.entity.RequestStatus;
import org.debugroom.wedding.domain.model.entity.RequestStatusPK;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.repository.basic.FindNotRequestUsersByRequestId;
import org.debugroom.wedding.domain.repository.basic.FindUsersByRequestIdAndIsApproved;
import org.debugroom.wedding.domain.repository.basic.RequestRepository;
import org.debugroom.wedding.domain.repository.basic.RequestStatusRepository;
import org.debugroom.wedding.domain.repository.common.UserRepository;
import org.debugroom.wedding.domain.service.common.UpdateResult;
import org.debugroom.wedding.domain.service.common.UserSharedService;

@Transactional
@Service("requestManagementService")
public class RequestManagementServiceImpl implements RequestManagementService{

	@Inject
	Mapper mapper;
	
	@Inject
	RequestRepository requestRepository;
	
	@Inject
	RequestStatusRepository requestStatusRepository;

	@Inject
	UserSharedService userSharedService;
	
	@Inject
	UserRepository userRepository;
	
	@Override
	public List<Request> getRequests() {
		return null;
	}

	@Override
	public Page<Request> getRequestsUsingPage(Pageable pageable) {
		return requestRepository.findAll(pageable);
	}

	@Override
	public RequestDetail getRequestDetail(String requestId) {
		Request request = requestRepository.findOne(requestId);
		List<User> approvedUsers = userRepository.findAll(
				FindUsersByRequestIdAndIsApproved.builder()
													.requestId(requestId)
													.isApproved(true)
													.build());
		List<User> deniedUsers = userRepository.findAll(
				FindUsersByRequestIdAndIsApproved.builder()
													.requestId(requestId)
													.isApproved(false)
													.build());
		return RequestDetail.builder()
				.request(request)
				.approvedUsers(approvedUsers)
				.deniedUsers(deniedUsers)
				.build();
	}

	@Override
	public List<User> getNotRequestUsers(String requestId) {
		FindNotRequestUsersByRequestId spec = FindNotRequestUsersByRequestId
												.builder().requestId(requestId)
												.build();
		return userRepository.findAll(spec);
	}

	@Override
	public UpdateResult<RequestDetail> updateRequest(RequestDraft requestDraft) {
		
		Request request = requestDraft.getRequest();
		UpdateResult<RequestDetail> updateResult = new UpdateResult<RequestDetail>();
		
		RequestDetail updateTargetRequestDetail = getRequestDetail(request.getRequestId());
		Request updateTargetRequest = updateTargetRequestDetail.getRequest();
		RequestDetail beforeUpdate = mapper.map(updateTargetRequestDetail, RequestDetail.class,
				"requestDetailMappingExcludingUserRelatedEntity");
		
		boolean isChangedRequest = false;
		List<String> updateParamList = new ArrayList<String>();
		
		if(!updateTargetRequest.getTitle().equals(request.getTitle())){
			updateTargetRequest.setTitle(request.getTitle());
			updateTargetRequest.setLastUpdatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			updateParamList.add("request.title");
			isChangedRequest = true;
		}
		
		request.setRequestContents(StringUtils.deleteWhitespace(request.getRequestContents()));

		if(null != request.getRequestContents()){
			if(!updateTargetRequest.getRequestContents().equals(request.getRequestContents())){
				updateTargetRequest.setRequestContents(request.getRequestContents());
				updateTargetRequest.setLastUpdatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				updateParamList.add("request.requestContents");
				isChangedRequest = true;
			}
		}
		
		if(null != requestDraft.getExcludeUsers()){
			for(User excludeUser : requestDraft.getExcludeUsers()){
				if(null != excludeUser.getUserId()){
					Iterator<RequestStatus> iterator = updateTargetRequest.getRequestStatuses().iterator();
					while(iterator.hasNext()){
						RequestStatus requestStatus = iterator.next();
						if(excludeUser.getUserId().equals(requestStatus.getId().getUserId())){
							iterator.remove();
    						updateParamList.add(new StringBuilder()
												.append("excludeUser-")
												.append(excludeUser.getUserId())
												.toString());
						}
					}
				}
			}
		}

		if(null != requestDraft.getAcceptors()){
			for(User acceptor : requestDraft.getAcceptors()){
				if(null != acceptor.getUserId()){
					
					updateTargetRequest.addRequestStatus(
						RequestStatus.builder()
										.id(RequestStatusPK.builder()
												.requestId(request.getRequestId())
												.userId(acceptor.getUserId())
												.build())
										.lastUpdatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()))
										.isAnswered(false)
										.isApproved(false)
										.ver(0)
										.build());
					updateParamList.add(new StringBuilder()
											.append("acceptor-")
											.append(acceptor.getUserId())
											.toString());
				}
			}
			updateTargetRequestDetail.setDeniedUsers(userRepository.findAll());
		}
		
		if(isChangedRequest){
			requestStatusRepository.updateIsAnsweredByRequestId(false, request.getRequestId());
		}

		updateResult.setUpdateParamList(updateParamList);
		updateResult.setBeforeEntity(beforeUpdate);
		updateResult.setAfterEntity(updateTargetRequestDetail);
		
		return updateResult;
	}

	@Override
	public List<User> getUsers() {
		return userSharedService.getUsers();
	}

	@Override
	public RequestDraft createRequestDraft(RequestDraft requestDraft) {
		String sequence = new StringBuilder()
								.append("0000")
								.append(requestRepository.count())
								.toString();
		String newRequestId = StringUtils.substring(sequence, 
				sequence.length() - 4, sequence.length());
		Request request = requestDraft.getRequest();
		request.setRequestId(newRequestId);
		request.setRegistratedDate(new Timestamp(
				Calendar.getInstance().getTimeInMillis()));
		return requestDraft;
	}

	@Override
	public Request saveRequest(RequestDraft requestDraft) {
		
		Request request = requestDraft.getRequest();
		
		request.setLastUpdatedDate(new Timestamp(
				Calendar.getInstance().getTimeInMillis()));
		
		Set<RequestStatus> requestStatuses = new HashSet<RequestStatus>();
		
		for(User user : requestDraft.getAcceptors()){
			requestStatuses.add(RequestStatus.builder()
									.id(RequestStatusPK.builder()
											.userId(user.getUserId())
											.requestId(request.getRequestId())
											.build())
									.isAnswered(false)
									.isApproved(false)
									.lastUpdatedDate(new Timestamp(
											Calendar.getInstance().getTimeInMillis()))
									.ver(0)
									.build());
		}
		request.setRequestStatuses(requestStatuses);
		return requestRepository.save(request);
	}

	@Override
	public Request deleteRequest(String requestId) {
		Request deleteRequest = requestRepository.findOne(requestId);
		requestRepository.delete(deleteRequest);
		return deleteRequest;
	}

}
