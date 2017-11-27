package org.debugroom.wedding.app.web.helper.aws;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.framework.common.exception.SystemException;
import org.debugroom.wedding.app.model.gallery.Movie;
import org.debugroom.wedding.app.model.gallery.Photo;
import org.debugroom.wedding.app.web.helper.ImageDownloadHelper;
import org.debugroom.wedding.domain.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Profile({"LocalAws","aws"})
@Component
public class ImageDownloadHelperImpl implements ImageDownloadHelper{

	private static final String S3_BUCKET_PREFIX = "s3://";
	@Value("${bucket.name}")
	private String bucketName;
	
	@Value("${profile.root.directory}")
	private String profileRootDirectory;
	
	@Inject
	private ResourceLoader resourceLoader;

	@Override
	public BufferedImage getProfileImage(User user) throws BusinessException {
		Resource resource = resourceLoader.getResource(new StringBuilder()
				.append(S3_BUCKET_PREFIX)
				.append(bucketName)
				.append("/")
				.append(profileRootDirectory)
				.append("/")
				.append(user.getImageFilePath())
				.toString());
		BufferedImage image = null;
		try {
			image = ImageIO.read(resource.getInputStream());
		} catch (IOException e) {
			throw new SystemException("imageDownloadHelper.error.0001", e);
		}
		return image;
	}

	@Override
	public String getMediaType(String fileName) {
		return new org.debugroom.wedding.app.web.helper.ImageDownloadHelperImpl()
				.getMediaType(fileName);
	}

	@Override
	public HttpHeaders getHeaders(Photo photo) {
		return new org.debugroom.wedding.app.web.helper.ImageDownloadHelperImpl()
				.getHeaders(photo);
	}

	@Override
	public BufferedImage getGalleryThumbnailImage(Photo photo) throws BusinessException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public BufferedImage getGalleryThumbnailImage(Movie movie) throws BusinessException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public BufferedImage getGalleryImage(Photo photo) throws BusinessException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
