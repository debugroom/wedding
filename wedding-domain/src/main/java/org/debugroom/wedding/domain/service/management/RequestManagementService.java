package org.debugroom.wedding.domain.service.management;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.debugroom.wedding.domain.model.entity.Request;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.service.common.UpdateResult;

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
