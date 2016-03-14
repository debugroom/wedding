package org.debugroom.wedding.domain.service.management;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.dozer.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.model.entity.Infomation;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.repository.basic.InfomationRepository;
import org.debugroom.wedding.domain.repository.common.UserRepository;

@Service("infomationManagementService")
public class InfomationManagementServiceImpl implements InfomationManagementService{

	@Inject
	InfomationRepository infomationRepository;
	
	@Inject
	UserRepository userRepository;
	
	@Override
	public Page<Infomation> getInfomationUsingPage(Pageable pageable) {
		return infomationRepository.findAll(pageable);
	}

	@Override
	public InfomationDetail getInfomationDetail(String infoId) {
		Infomation infomation = infomationRepository.findOne(infoId);
		List<User> accessedUsers = userRepository
				.findUsersByInfoIdAndIsAccessed(infoId, true);
		List<User> noAccessedUsers = userRepository
				.findUsersByInfoIdAndIsAccessed(infoId, false);
		return InfomationDetail.builder()
					.infomation(infomation)
					.accessedUsers(accessedUsers)
					.noAccessedUsers(noAccessedUsers)
					.build();
	}

}
