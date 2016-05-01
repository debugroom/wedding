package org.debugroom.wedding.domain.service.basic;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.model.entity.Infomation;
import org.debugroom.wedding.domain.model.entity.Request;

/**
 * 
 * @author org.debugroom
 *
 */
public interface PortalService {

	public PortalInfoOutput getPortalInfo(User user) throws BusinessException;
	
	public Infomation getInfomation(String infoId);
	
	public Request getRequest(String requestId);
	
}
