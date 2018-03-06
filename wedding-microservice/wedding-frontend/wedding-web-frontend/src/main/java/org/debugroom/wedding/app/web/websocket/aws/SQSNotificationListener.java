package org.debugroom.wedding.app.web.websocket.aws;

import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.model.aws.gallery.CreateThumbnailNotification;
import org.debugroom.wedding.app.model.aws.gallery.UploadFileResult;
import org.debugroom.wedding.app.model.aws.gallery.Media;
import org.debugroom.wedding.app.web.adapter.docker.provider.ConnectPathProvider;
import org.debugroom.wedding.app.web.helper.aws.GalleryContentsDownloadHelper;
import org.debugroom.wedding.app.web.helper.aws.GalleryContentsUploadHelper;
import org.debugroom.wedding.app.web.util.RequestBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SQSNotificationListener {

	private static final String APP_NAME = "api/v1";

	@Value("${server.contextPath}")
	private String contextPath;

	@Inject
	SimpMessagingTemplate simpMessagingTemplate;
	
	@Inject
	ConnectPathProvider provider;
	
	@Inject
	GalleryContentsUploadHelper uploadHelper;
	
	@Inject
	GalleryContentsDownloadHelper downloadHelper;
	
	@Inject
	ObjectMapper objectMapper;
	
	@SqsListener(value = "CreateThumbnailNotify", 
			deletionPolicy=SqsMessageDeletionPolicy.ON_SUCCESS)
	public void createThumbnailNotification(String message) throws BusinessException, JsonParseException, JsonMappingException, IOException{
		CreateThumbnailNotification createThumbnailNotification = 
				objectMapper.readValue(message, CreateThumbnailNotification.class);
		Media media = uploadHelper.createMedia(StringUtils.substringAfterLast(
				createThumbnailNotification.getOriginalObjectKey(), "/"), 
				createThumbnailNotification.getUserId(), 
				createThumbnailNotification.getFolderId());
		String serviceName = "gallery";
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate(
				createThumbnailNotification.getUserId(), UUID.randomUUID().toString());
		UploadFileResult uploadFileResult = new UploadFileResult();
		uploadFileResult.setMedia(restTemplate.postForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/media/new")
						.toString(), provider).toUri(), media, Media.class));
		uploadFileResult.setThumbnailPresignedUrl(
				downloadHelper.getPresignedUrl(media.getThumbnailFilePath()).toString());
		uploadFileResult.getMedia().setRequestContextPath(contextPath);
		simpMessagingTemplate.convertAndSend(
				new StringBuilder()
				.append("/notification/folder-")
				.append(createThumbnailNotification.getFolderId())
				.toString(), 
				uploadFileResult);
	}

}
