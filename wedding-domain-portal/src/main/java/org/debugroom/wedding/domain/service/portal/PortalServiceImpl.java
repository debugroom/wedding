package org.debugroom.wedding.domain.service.portal;

import java.util.Calendar;

import javax.inject.Inject;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.entity.Information;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.entity.portal.Request;
import org.debugroom.wedding.domain.model.portal.PortalInfoOutput;
import org.debugroom.wedding.domain.repository.jpa.InformationRepository;
import org.debugroom.wedding.domain.repository.jpa.portal.RequestRepository;
import org.debugroom.wedding.domain.service.common.UserSharedService;
import org.springframework.stereotype.Service;

@Service("portalService")
public class PortalServiceImpl implements PortalService{

	@Inject
	UserSharedService userSharedService;
	
	@Inject
	InformationRepository infomationRepository;
	
	@Inject
	RequestRepository requestRepository;
	
	@Override
	public PortalInfoOutput getPortalInfo(User user)  {
		PortalInfoOutput output = PortalInfoOutput.builder().build();
		try {
			output.setUser(userSharedService.getUser(user.getUserId()));
			output.setInformationList(infomationRepository
					.findUserByUserIdAndReleaseDateLessThan(
						user.getUserId(), Calendar.getInstance().getTime()));
		} catch (BusinessException e) {
			new BusinessException("portalService.error.0001");
		}
		return output;
	}

	@Override
	public Information getInformation(String infoId) {
		return infomationRepository.findOne(infoId);
	}

	@Override
	public Request getRequest(String requestId) {
		return requestRepository.findOne(requestId);
	}

}
