package org.debugroom.wedding.domain.service.management;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.service.common.UpdateResult;
import org.debugroom.wedding.domain.model.entity.Infomation;
import org.debugroom.wedding.domain.model.entity.User;

public interface InfomationManagementService {

	public Page<Infomation> getInfomationUsingPage(Pageable pageable);

	public InfomationDetail getInfomationDetail(String infoId);
	
	public UpdateResult<InfomationDetail> updateInfomationService(
			Infomation infomation, String rootPathForMessage, 
			String messageBody) throws BusinessException;

	public InfomationDraft createInfomationDraft(InfomationDraft infomationDraft,
			String rootPathForMessage) throws BusinessException;

	public Infomation saveInfomation(InfomationDraft infomationDraft) throws BusinessException;

	public List<User> getUsers();
		
}
