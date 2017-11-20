package org.debugroom.wedding.domain.service.portal;

import org.debugroom.framework.common.exception.BusinessException;

import org.debugroom.wedding.domain.entity.Information;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.entity.portal.Request;
import org.debugroom.wedding.domain.model.portal.PortalInfoOutput;


public interface PortalService {

	public PortalInfoOutput getPortalInfo(User user) throws BusinessException;

	public Information getInformation(String infoId);
	
	public Request getRequest(String requestId);

	public User getUser(String userId) throws BusinessException;
	
	public boolean updateRequestStatus(String userId, String requestId, boolean isApproved);

}
