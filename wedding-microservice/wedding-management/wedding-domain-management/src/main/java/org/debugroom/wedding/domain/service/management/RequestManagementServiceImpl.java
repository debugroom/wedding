package org.debugroom.wedding.domain.service.management;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.debugroom.wedding.domain.entity.management.Request;
import org.debugroom.wedding.domain.entity.management.RequestStatus;
import org.debugroom.wedding.domain.entity.management.RequestStatusPK;
import org.debugroom.wedding.domain.entity.management.User;
import org.debugroom.wedding.domain.model.common.UpdateResult;
import org.debugroom.wedding.domain.model.management.RequestDetail;
import org.debugroom.wedding.domain.model.management.RequestDraft;
import org.debugroom.wedding.domain.repository.jpa.management.RequestRepository;
import org.debugroom.wedding.domain.repository.jpa.management.RequestStatusRepository;
import org.debugroom.wedding.domain.repository.jpa.management.ManagementUserRepository;
import org.debugroom.wedding.domain.repository.jpa.spec.management.FindNotRequestUsersByRequestId;
import org.debugroom.wedding.domain.repository.jpa.spec.management.FindUsersByRequestIdAndIsApproved;
import org.debugroom.wedding.domain.service.common.DateUtil;

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
	ManagementUserRepository userRepository;
	
	@Override
	public List<Request> getRequests() {
		return requestRepository.findAll();
	}

	@Override
	public Page<Request> getReqesutsUsingPage(Pageable pageable) {
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
		return userRepository.findAll(
				FindNotRequestUsersByRequestId.builder().requestId(requestId).build());
	}

	@Override
	public UpdateResult<RequestDetail> updateRequest(RequestDraft requestDraft) {
		Request request = requestDraft.getRequest();
		UpdateResult<RequestDetail> updateResult = new UpdateResult<RequestDetail>();
		
		RequestDetail updateTargetRequestDetail = getRequestDetail(request.getRequestId());
		Request updateTargetRequest = updateTargetRequestDetail.getRequest();
		RequestDetail beforeUpdate = 
				mapper.map(updateTargetRequestDetail, RequestDetail.class);

		boolean isChangedRequest = false;
		List<String> updateParamList = new ArrayList<String>();
		
		if(!updateTargetRequest.getTitle().equals(request.getTitle())){
			updateTargetRequest.setTitle(request.getTitle());
			updateTargetRequest.setLastUpdatedDate(DateUtil.getCurrentDate());
			updateParamList.add("request.title");
			isChangedRequest = true;
		}
			
		request.setRequestContents(StringUtils.deleteWhitespace(request.getRequestContents()));
		
		if(null != request.getRequestContents()){
			if(!updateTargetRequest.getRequestContents().equals(request.getRequestContents())){
				updateTargetRequest.setRequestContents(request.getRequestContents());
				updateTargetRequest.setLastUpdatedDate(DateUtil.getCurrentDate());
				updateParamList.add("request.requestContents");
				isChangedRequest = true;
			}
		}
		
		if(null != requestDraft.getExcludeUsers()){
			for(User excludeUser : requestDraft.getExcludeUsers()){
				if(null != excludeUser.getUserId()){
					Iterator<RequestStatus> iterator = 
							updateTargetRequest.getRequestStatuses().iterator(); 
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
											.lastUpdatedDate(DateUtil.getCurrentDate())
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
			requestStatusRepository.updateIsAnsweredByRequestId(
					false, request.getRequestId());
		}
		
		updateResult.setUpdateParamList(updateParamList);
		updateResult.setBeforeEntity(beforeUpdate);
		updateResult.setAfterEntity(updateTargetRequestDetail);

		return updateResult;
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public RequestDraft createRequestDraft(RequestDraft requestDraft) {
		Request request = requestDraft.getRequest();
		request.setRequestId(getNewRequestId());
		request.setRegistratedDate(DateUtil.getCurrentDate());
		return requestDraft;
	}

	@Override
	public Request saveRequest(RequestDraft requestDraft) {
		
		Request request = requestDraft.getRequest();
		
		request.setLastUpdatedDate(DateUtil.getCurrentDate());
		
		Set<RequestStatus> requestStatuses = new HashSet<RequestStatus>();
		
		for(User user : requestDraft.getAcceptors()){
			requestStatuses.add(RequestStatus.builder()
								.id(RequestStatusPK.builder()
										.userId(user.getUserId())
										.requestId(request.getRequestId())
										.build())
								.isAnswered(false)
								.isApproved(false)
								.lastUpdatedDate(DateUtil.getCurrentDate())
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

	private String getNewRequestId(){
		Request request = requestRepository.findTopByOrderByRequestIdDesc();
		StringBuilder stringBuilder = new StringBuilder().append("0000");
		if(null != request){
			if(!"0000".equals(request.getRequestId())){
				stringBuilder.append(Integer.parseInt(StringUtils.stripStart(
						request.getRequestId(), "0"))+1);
			}else{
				stringBuilder.append("1");
			}
			
		}
		String sequence = stringBuilder.toString();
		return StringUtils.substring(
				sequence, sequence.length()-4, sequence.length());
	}

	@Override
	public Request getRequest(String requestId) {
		return requestRepository.findOne(requestId);
	}

}
