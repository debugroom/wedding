package org.debugroom.wedding.app.web.helper;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.model.management.information.InformationDraft;
import org.debugroom.wedding.domain.entity.Information;

@Component
public class InformationMessageBodyHelper {

	@Value("${info.root.directory}")
	private String infoRootDirectory;
	
	public void saveMessageBody(InformationDraft informationDraft, String messsageBody) 
			throws BusinessException{
		
		String inforPagePath = new StringBuilder()
				.append("info")
				.append(java.io.File.separator)
				.append(informationDraft.getInformation().getInfoId())
				.append("_")
				.append(informationDraft.getInfoName())
				.append(".jsp")
				.toString();
		
		File file = new File(new StringBuilder()
				.append(infoRootDirectory)
				.append(java.io.File.separator)
				.append(inforPagePath)
				.toString());
		
		try{
			FileUtils.writeStringToFile(file, messsageBody, "UTF-8");
		} catch(IOException e){
			throw new BusinessException(
					"informationMessageBodyHelper.error.0001", e, "");
		}
		informationDraft.getInformation().setInfoPagePath(inforPagePath);
	}

	public String getMessageBody(Information information) throws BusinessException{
		
		File file = new File(new StringBuilder()
				.append(infoRootDirectory)
				.append(java.io.File.separator)
				.append(information.getInfoPagePath())
				.toString());
		try {
			if(!StringUtils.startsWith(file.getCanonicalPath(), infoRootDirectory)){
				throw new BusinessException("createInformationMessageBodyHelper.error.0003", 
						null, information.getInfoPagePath());
			}
			return FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {
			throw new BusinessException(
					"informationMessageBodyHelper.error.0002", e, information.getInfoId());
		}
	}
	
	public boolean updateMessageBody(Information information, String newMessageBody) 
			throws BusinessException{
		
		boolean isUpdated = false;
		
		if(null != newMessageBody){
			String oldMessageBody = getMessageBody(information);
			if(!newMessageBody.equals(oldMessageBody)){
				File file = new File(new StringBuilder()
						.append(infoRootDirectory)
						.append(java.io.File.separator)
						.append(information.getInfoPagePath())
						.toString());
				try {
					FileUtils.writeStringToFile(file, newMessageBody, "UTF-8");
					isUpdated = true;
				} catch (IOException e) {
					throw new BusinessException(
						"informationMessageBodyHelper.error.0001", e, "");
				}
			}
		}
		return isUpdated;
	}
}
