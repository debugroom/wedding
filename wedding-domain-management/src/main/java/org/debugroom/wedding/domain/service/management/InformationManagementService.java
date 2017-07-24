package org.debugroom.wedding.domain.service.management;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.entity.Information;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.model.common.UpdateResult;
import org.debugroom.wedding.domain.model.management.InformationDetail;
import org.debugroom.wedding.domain.model.management.InformationDraft;

public interface InformationManagementService {

	public Page<Information> getInformationUsingPage(Pageable pageable);
	
	public InformationDetail getInformationDetail(String infoId);
	
	public UpdateResult<InformationDetail> updateInformation(
			InformationDraft informationDraft) throws BusinessException;
	
	public InformationDraft createInformationDraft(InformationDraft informationDraft) throws BusinessException;
	
	public Information saveInformation(InformationDraft informationDraft) throws BusinessException;
	
	public List<User> getUsers();
	
	public List<User> getNoViewers(String infoId);

	public Information deleteInformation(String infoId);

 }
