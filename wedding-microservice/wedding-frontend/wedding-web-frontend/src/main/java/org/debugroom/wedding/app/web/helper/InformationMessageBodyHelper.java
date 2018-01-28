package org.debugroom.wedding.app.web.helper;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.model.management.information.InformationDraft;
import org.debugroom.wedding.domain.entity.Information;

public interface InformationMessageBodyHelper {

	public void saveMessageBody(InformationDraft informationDraft, String messsageBody) 
			throws BusinessException;
	
	public String getMessageBody(Information information) throws BusinessException;
	
	public boolean updateMessageBody(Information information, String newMessageBody) 
			throws BusinessException;

}
