package org.debugroom.wedding.domain.service.management;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.debugroom.wedding.domain.model.common.UpdateResult;
import org.debugroom.wedding.domain.model.management.RequestDetail;
import org.debugroom.wedding.domain.model.management.RequestDraft;
import org.debugroom.wedding.domain.entity.management.Request;
import org.debugroom.wedding.domain.entity.management.User;

public interface RequestManagementService {

	public List<Request> getRequests();
	
	public Request getRequest(String requestId);

	public Page<Request> getReqesutsUsingPage(Pageable pageable);

	public RequestDetail getRequestDetail(String requestId);

	public List<User> getNotRequestUsers(String requestId);
	
	public UpdateResult<RequestDetail> updateRequest(RequestDraft requestDraft);
	
	public List<User> getUsers();
	
	public RequestDraft createRequestDraft(RequestDraft requestDraft);
	
	public Request saveRequest(RequestDraft requestDraft);
	
	public Request deleteRequest(String requestId);

}