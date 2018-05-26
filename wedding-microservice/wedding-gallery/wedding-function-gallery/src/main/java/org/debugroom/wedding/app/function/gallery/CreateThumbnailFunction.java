package org.debugroom.wedding.app.function.gallery;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.bytedeco.javacv.FFmpegFrameFilter;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.model.gallery.CreateThumbnailNotification;
import org.debugroom.wedding.app.model.gallery.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.sqs.AmazonSQS;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class CreateThumbnailFunction implements 
	Function<Flux<S3EventNotification>, Flux<String>>{

	@Value("${gallery.image.thumbnail.width}")
	private int galleryImageThumbnailWidth;
	@Value("${gallery.image.thumbnail.height}")
	private int galleryImageThumbnailHeight;
	@Value("${gallery.movie.thumbnail.frame.start}")
	private int galleryMovieThumbnailFrameStart;
	
	@Value("${gallery.create.thumbnail.sqs.notification.queue.name:CreateThumbnailNotify}")
	private String galleryCreateThumbnailSqsNotificationQueueName;

	@Inject
	ObjectMapper mapper;
	
	@Inject
	AmazonS3 amazonS3;
	
	@Inject
	QueueMessagingTemplate queueMessagingTemplate;
	
	@Override
	public Flux<String> apply(Flux<S3EventNotification> event) {
		event.subscribe(s -> {
			if(0 != s.getRecords().size()){
				S3EventNotification.S3EventNotificationRecord record = s.getRecords().get(0);
				String keyName = record.getS3().getObject().getKey();
				S3Object s3Object = amazonS3.getObject(
						record.getS3().getBucket().getName(), keyName);
				BufferedImage bufferedImage = new BufferedImage(galleryImageThumbnailWidth, 
						galleryImageThumbnailHeight, BufferedImage.TYPE_INT_RGB);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				InputStream inputStream = null;
				try {
					switch(MediaType.getMediaType(keyName)){
					case PHOTOGRAPH:
						bufferedImage.getGraphics().drawImage(
							ImageIO.read(s3Object.getObjectContent()).getScaledInstance(
									galleryImageThumbnailWidth, galleryImageThumbnailHeight, 
									Image.SCALE_SMOOTH), 0, 0, null);
						break;
					case MOVIE:
						FFmpegFrameGrabber ffmpegFrameGrabber = new FFmpegFrameGrabber(
								s3Object.getObjectContent());
						Java2DFrameConverter java2dFrameConverter = new Java2DFrameConverter();
						
						ffmpegFrameGrabber.start();
						int count = 0;
						while(ffmpegFrameGrabber.getFrameNumber() < 
								ffmpegFrameGrabber.getLengthInFrames()){
							if(galleryMovieThumbnailFrameStart < count){
								bufferedImage = java2dFrameConverter.convert(
										ffmpegFrameGrabber.grab());
								if(bufferedImage == null){
									continue;
								}
								ffmpegFrameGrabber.stop();
								ffmpegFrameGrabber.release();
								break;
							}
							count++;
						}
						break;
					default:
						new BusinessException("No support format.");
					}
					ImageIO.write(bufferedImage, "jpg", outputStream);

					inputStream = new ByteArrayInputStream(outputStream.toByteArray());
					ObjectMetadata objectMetadata = new ObjectMetadata();
					objectMetadata.setContentLength(outputStream.size());
					
					String newKey = createNewThumbnailObjectKey(s3Object.getKey(),
							s3Object.getObjectMetadata().getUserMetaDataOf("user-id"));
					amazonS3.putObject(s3Object.getBucketName(), newKey, inputStream, objectMetadata);
					CreateThumbnailNotification createThumbnailNotification = 
							CreateThumbnailNotification.builder()
							.folderId(s3Object.getObjectMetadata().getUserMetaDataOf("folder-id"))
							.userId(s3Object.getObjectMetadata().getUserMetaDataOf("user-id"))
							.originalObjectKey(keyName)
							.thumbnailObjectKey(newKey)
							.build();
		            queueMessagingTemplate.convertAndSend(galleryCreateThumbnailSqsNotificationQueueName, 
		            		mapper.writeValueAsString(createThumbnailNotification));
				log.info(new StringBuilder()
						.append(" Thumbnail ")
						.append(newKey)
						.append(" created.")
						.toString());
				} catch (Exception e) {
					log.error(new StringBuilder()
							.append(e.getMessage())
							.append(" : ")
							.append(keyName)
							.append(" : ")
							.append(e.getCause().toString())
							.toString());
				} finally{
					try {
						outputStream.close();
						inputStream.close();
						s3Object.close();
					} catch (Exception e) {
						log.error(new StringBuilder()
								.append(e.getMessage())
								.append("Stream close error : ")
								.append(keyName)
								.append(" : ")
								.append(e.getCause().toString())
								.toString());
					}
				}
			}else{
				log.info("No event data.");
			}
		});
		return Flux.just(new StringBuilder()
				.append("Create Thumbnail Process Complete!")
				.toString());
	}

	private String createNewThumbnailObjectKey(String keyName, String userId){
		String newTempKeyPath = StringUtils.substringBeforeLast(
				StringUtils.substringBeforeLast(keyName, "/"), "/");
		String newKeyPath = new StringBuilder()
				.append(StringUtils.substringBeforeLast(newTempKeyPath, "/"))
				.append("/thumbnail/").append(userId).toString();
		String newTempObjectKey = StringUtils.substringAfterLast(keyName, "/");
		String newObjectKeyName = new StringBuilder()
				.append(StringUtils.substringBeforeLast(newTempObjectKey, "."))
				.append(".jpg")
				.toString();
		return new StringBuilder()
				.append(newKeyPath)
				.append("/")
				.append(newObjectKeyName)
				.toString();
	}

}
