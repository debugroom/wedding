package org.debugroom.wedding.domain.management.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.debugroom.wedding.domain.common.model.UpdateResult;
import org.debugroom.wedding.domain.management.model.RequestDetail;
import org.debugroom.wedding.domain.management.model.RequestDraft;
import org.debugroom.wedding.domain.model.entity.Request;
import org.debugroom.wedding.domain.model.entity.User;

public interface RequestManagementService {

	public List<Request> getRequests();
	
	public Page<Request> getRequestsUsingPage(Pageable pageable);
	
	public RequestDetail getRequestDetail(String requestId);

	public List<User> getNotRequestUsers(String requestId);
	
	public UpdateResult<RequestDetail> updateRequest(RequestDraft requestDraft);
	
	public List<User> getUsers();
	
	public RequestDraft createRequestDraft(RequestDraft requestDraft);
		
	public Request saveRequest(RequestDraft requestDraft);

	public Request deleteRequest(String requestId);
	
}
