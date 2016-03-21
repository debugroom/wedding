package org.debugroom.wedding.domain.service.management;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.dozer.Mapper;

import org.apache.commons.io.FileUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.common.DomainProperties;
import org.debugroom.wedding.domain.model.entity.Infomation;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.repository.basic.InfomationRepository;
import org.debugroom.wedding.domain.repository.basic.NotificationRepository;
import org.debugroom.wedding.domain.repository.common.UserRepository;
import org.debugroom.wedding.domain.service.common.UpdateResult;

@Transactional
@Service("infomationManagementService")
public class InfomationManagementServiceImpl implements InfomationManagementService{

	@Inject
	InfomationRepository infomationRepository;
	
	@Inject
	UserRepository userRepository;
	
	@Inject
	NotificationRepository notificationRepository;
	
	@Inject
	DomainProperties domainProperties;
	
	@Inject
	Mapper mapper;
	
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

	@Override
	public UpdateResult<InfomationDetail> updateInfomationService(
			Infomation infomation, String messageBody) throws BusinessException {
		
		UpdateResult<InfomationDetail> updateResult = new UpdateResult<InfomationDetail>();
		
		InfomationDetail updateTargetInfomationDetail = getInfomationDetail(infomation.getInfoId());
		Infomation updateTargetInfomation = updateTargetInfomationDetail.getInfomation();
		InfomationDetail beforeUpdate = mapper.map(updateTargetInfomationDetail, InfomationDetail.class);

		List<String> updateParamList = new ArrayList<String>();
		
		if(!updateTargetInfomation.getTitle().equals(infomation.getTitle())){
			updateTargetInfomation.setTitle(infomation.getTitle());
			updateTargetInfomation.setLastUpdatedDate(new Date());
			updateParamList.add("infomation.title");
		}
		
		if(updateTargetInfomation.getReleaseDate() != infomation.getReleaseDate()){
			updateTargetInfomation.setReleaseDate(infomation.getReleaseDate());
			updateTargetInfomation.setLastUpdatedDate(new Date());
			updateParamList.add("infomation.releaseDate");
		}
		
		if(null != messageBody){
			File file = new File(domainProperties.getInfoRootPath() + infomation.getInfoPagePath());
			try {
				FileUtils.writeStringToFile(file, messageBody, "UTF-8");
			} catch (IOException e) {
				throw new BusinessException(
						"infomationManagementService.error.0001", e, infomation.getInfoId());
			}
			updateParamList.add("infomation.messageBody");
		}
		
		if(updateParamList.size() != 0){
			notificationRepository.updateIsAccessedByInfoId(false, infomation.getInfoId());
		}
		
		updateResult.setUpdateParamList(updateParamList);
		updateResult.setBeforeEntity(beforeUpdate);
		updateResult.setAfterEntity(updateTargetInfomationDetail);

		return updateResult;

	}
}
