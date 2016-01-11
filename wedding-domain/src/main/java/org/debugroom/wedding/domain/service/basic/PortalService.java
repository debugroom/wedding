package org.debugroom.wedding.domain.service.basic;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.model.entity.User;

/**
 * 
 * @author org.debugroom
 *
 */
public interface PortalService {

	public PortalInfoOutput getPortalInfo(User user) throws BusinessException;
}
