package org.debugroom.wedding.app.web.helper.aws;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.model.management.information.InformationDraft;
import org.debugroom.wedding.app.web.helper.InformationMessageBodyHelper;
import org.debugroom.wedding.domain.entity.Information;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

@Component
public class InformationMessageBodyHelperImpl implements InformationMessageBodyHelper{

	private static final String S3_BUCKET_PREFIX = "s3://";
	@Value("${bucket.name}")
	private String bucketName;
	@Value("${info.root.directory}")
	private String infoRootDirectory;
	
	@Inject
	private ResourceLoader resourceLoader;

	@Override
	public String getMessageBody(Information information) throws BusinessException {
		Resource resource = resourceLoader.getResource(new StringBuilder()
				.append(S3_BUCKET_PREFIX)
				.append(bucketName)
				.append("/")
				.append(information.getInfoPagePath())
				.toString());
		InputStream inputStream = null;
		try {
		    inputStream = resource.getInputStream();
		    String messageBody = IOUtils.toString(inputStream, "UTF-8");
		    return messageBody;
		} catch (IOException e) {
			throw new BusinessException(
					"informationMessageBodyHelper.error.0002", e, information.getInfoId());
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void saveMessageBody(InformationDraft informationDraft, String messageBody) 
			throws BusinessException {
		String s3UploadRootPath = new StringBuilder()
				.append(S3_BUCKET_PREFIX)
				.append(bucketName)
				.append("/")
				.toString();
		String informationDirectory = new StringBuilder()
				.append(infoRootDirectory)
				.append("/")
				.toString();
		String objectKey = new StringBuilder()
				.append(informationDraft.getInformation().getInfoId())
				.append("_")
				.append(informationDraft.getInfoName())
				.append(".jsp")
				.toString();
		
		WritableResource writableResource = (WritableResource)
				resourceLoader.getResource(new StringBuilder()
						.append(s3UploadRootPath)
						.append(informationDirectory)
						.append(objectKey)
						.toString());
		try (OutputStream outputStream = writableResource.getOutputStream()){
			IOUtils.write(messageBody, outputStream, "UTF-8");
		} catch (IOException e) {
			throw new BusinessException(
					"informationMessageBodyHelper.error.0001", e, "");
		}
		informationDraft.getInformation().setInfoPagePath(
				new StringBuilder().append(informationDirectory)
				.append(objectKey).toString());
	}

	@Override
	public boolean updateMessageBody(Information information, String newMessageBody) 
			throws BusinessException {
		
		boolean isUpdated = false;
		
		if(null != newMessageBody){
			String oldMessageBody = getMessageBody(information);
			if(!newMessageBody.equals(oldMessageBody)){
				WritableResource writableResource = 
						(WritableResource)resourceLoader.getResource(
								new StringBuilder()
								.append(S3_BUCKET_PREFIX)
								.append(bucketName)
								.append("/")
								.append(information.getInfoPagePath())
								.toString());
				try(OutputStream outputStream 
						= writableResource.getOutputStream()){
					IOUtils.write(newMessageBody, outputStream, "UTF-8");
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
