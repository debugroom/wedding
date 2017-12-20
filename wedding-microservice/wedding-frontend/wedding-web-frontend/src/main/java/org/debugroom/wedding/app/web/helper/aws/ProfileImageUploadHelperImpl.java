package org.debugroom.wedding.app.web.helper.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.framework.spring.webmvc.fileupload.FileUploadHelper;

@Component("profileImageUploadHelper")
public class ProfileImageUploadHelperImpl implements FileUploadHelper{

	private static final String S3_BUCKET_PREFIX = "s3://";

	@Value("${bucket.name}")
	private String bucketName;
	@Value("${profile.root.directory}")
	private String profileRootDirectory;
	@Value("${profile.directory}")
	private String profileDirectory;
	@Value("${profile.image.file.name}")
	private String profileImageFileName;
	
	@Inject
	private ResourceLoader resourceLoader;
	@Inject
	private ResourcePatternResolver resourcePatternResolver;
	@Inject
	private AmazonS3 amazonS3;

	@Override
	public String saveFile(MultipartFile multipartFile, String userId) throws BusinessException {
		String s3UploadRootPath = new StringBuilder()
				.append(S3_BUCKET_PREFIX)
				.append(bucketName)
				.append("/")
				.toString();
		String userDirectory = new StringBuilder()
				.append(profileRootDirectory)
				.append("/")
				.append(profileDirectory)
				.append("/")
				.append(userId)
				.toString();
		String uuidString = UUID.randomUUID().toString();
		String imageDirectory = new StringBuilder()
				.append(userDirectory)
				.append("/")
				.append(uuidString)
				.toString();
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
		try {
			List<Resource> resources = Arrays.asList(resourcePatternResolver.getResources(
					new StringBuilder()
					.append(s3UploadRootPath)
					.append(userDirectory)
					.append("/**")
					.toString()));
			if(resources.size() == 0){
				PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
						new StringBuilder().append(userDirectory).append("/").toString(), 
						emptyContent, metadata);
				amazonS3.putObject(putObjectRequest);
			}
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, 
					new StringBuilder().append(imageDirectory).append("/").toString(), 
					emptyContent, metadata);
			amazonS3.putObject(putObjectRequest);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				emptyContent.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		StringBuilder stringBuilder = new StringBuilder().append(profileImageFileName);
		switch (multipartFile.getContentType()) {
			case MediaType.IMAGE_PNG_VALUE :
				stringBuilder.append(".png");
				break;
			case MediaType.IMAGE_JPEG_VALUE :
				stringBuilder.append(".jpg");
				break;
			case MediaType.IMAGE_GIF_VALUE :
				stringBuilder.append(".gif");
				break;
			default:
				stringBuilder.append(".jpg");
				break;
		}
		String objectKey = stringBuilder.toString();
		WritableResource writableResource = (WritableResource)resourceLoader.getResource(
				new StringBuilder()
				.append(s3UploadRootPath)
				.append(imageDirectory)
				.append("/")
				.append(objectKey)
				.toString());
		try (InputStream inputStream = multipartFile.getInputStream(); OutputStream outputStream = writableResource.getOutputStream()){
			IOUtils.copy(inputStream, outputStream);
		} catch (IOException e) {
			throw new BusinessException("imageUploadHelper.error.0001", null, userId);
		}
		return new StringBuilder()
				.append(profileDirectory)
				.append("/")
				.append(userId)
				.append("/")
				.append(uuidString)
				.append("/")
				.append(objectKey)
				.toString();
	}

}
