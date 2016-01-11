package org.debugroom.wedding.domain.service.basic;

import java.util.Calendar;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import org.debugroom.wedding.domain.service.common.UserSharedService;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.repository.basic.InfomationRepository;
import org.debugroom.wedding.domain.model.entity.User;

@Service
public class PortalServiceImpl implements PortalService{

	@Inject
	UserSharedService userSharedService;
	
	@Inject
	InfomationRepository infomationRepository;
	
	@Override
	public PortalInfoOutput getPortalInfo(User user)  {
		PortalInfoOutput output = PortalInfoOutput.builder().build();
		try {
			output.setUser(userSharedService.getUser(user.getUserId()));
			output.setInfomationList(infomationRepository
					.findUserByUserIdAndReleaseDateLessThan(
						user.getUserId(), Calendar.getInstance().getTime()));
		} catch (BusinessException e) {
			new BusinessException("portalService.error.0001");
		}
		return output;
	}

}
