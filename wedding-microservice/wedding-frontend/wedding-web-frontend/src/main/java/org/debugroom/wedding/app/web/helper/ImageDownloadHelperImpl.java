package org.debugroom.wedding.app.web.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.framework.common.exception.SystemException;
import org.debugroom.wedding.app.model.gallery.Movie;
import org.debugroom.wedding.app.model.gallery.Photo;
import org.debugroom.wedding.domain.entity.User;

//@Profile({"dev", "LocalDev"})
@Profile({"local"})
@Component
public class ImageDownloadHelperImpl implements ImageDownloadHelper{

	@Value("${profile.root.directory}")
	private String profileRootDirectory;
	
	@Value("${gallery.root.directory}")
	private String galleryRootDirectory;
	
	private static final String FILE_SEPALATOR = System.getProperty("file.separator");
	
	public BufferedImage getProfileImage(User user) throws BusinessException{
		String rootPath = new StringBuilder()
									.append(profileRootDirectory)
									.toString();
		String imageFilePath = new StringBuilder()
									.append(rootPath)
									.append(FILE_SEPALATOR)
									.append(user.getImageFilePath())
									.toString();

		return getImage(rootPath, imageFilePath);

	}

	public String getMediaType(String fileName){
		String extension = StringUtils.substringAfter(fileName, ".");
		String path = null;
		switch(extension){
			case "jpg":
				path = "photo";
				break;
			case "jpeg":
				path = "photo";
				break;
			case "png":
				path =  "photo";
				break;
			case "gif":
				path =  "photo";
				break;
			case "mpeg":
				path =  "movie";
				break;
			case "mp4":
				path =  "movie";
				break;
			case "wmv":
				path =  "movie";
				break;
			case "mov":
				path =  "movie";
				break;
			case "MOV":
				path =  "movie";
				break;
			case "avi":
				path =  "movie";
				break;
			case "m4v":
				path =  "movie";
				break;
		}
		return path;
	}
	public HttpHeaders getHeaders(Photo photo){
		String extension = StringUtils.substringAfter(
				photo.getFilePath(), "."); 
		HttpHeaders headers = new HttpHeaders();
		switch (extension){
			case "jpg":
				headers.setContentType(MediaType.IMAGE_JPEG);
			break;
			case "jpeg":
				headers.setContentType(MediaType.IMAGE_JPEG);
			break;
			case "png":
				headers.setContentType(MediaType.IMAGE_PNG);
			break;
			case "gif":
				headers.setContentType(MediaType.IMAGE_GIF);
			break;
		}
		return headers;
	}

	public BufferedImage getGalleryThumbnailImage(Photo photo) throws BusinessException{
		String rootPath = new StringBuilder()
									.append(galleryRootDirectory)
									.toString();
		String thumbnailImagePath = new StringBuilder()
											.append(rootPath)
											.append(FILE_SEPALATOR)
											.append(photo.getThumbnailFilePath())
											.toString();
		return getImage(rootPath, thumbnailImagePath);
	}
	
	public BufferedImage getGalleryThumbnailImage(Movie movie) throws BusinessException{
		String rootPath = new StringBuilder()
				.append(galleryRootDirectory)
				.toString();
		String thumbnailImagePath = new StringBuilder()
				.append(rootPath)
				.append(FILE_SEPALATOR)
				.append(movie.getThumbnailFilePath())
				.toString();
		return getImage(rootPath, thumbnailImagePath);
	}

	public BufferedImage getGalleryImage(Photo photo) throws BusinessException {
		String rootPath = new StringBuilder()
									.append(galleryRootDirectory)
									.toString();
		String imagePath = new StringBuilder()
											.append(rootPath)
											.append(FILE_SEPALATOR)
											.append(photo.getFilePath())
											.toString();
		return getImage(rootPath, imagePath);
	}

	private BufferedImage getImage(String rootPath, String imageFilePath) throws BusinessException{
		BufferedImage image = null;
		try {
			// ディレクトリトラバーサル対策：指定されたファイルパスが、イメージを保存しているルートパス配下にあるか確認する。
			if(!StringUtils.startsWith(new File(imageFilePath).getCanonicalPath(), rootPath)){
				throw new BusinessException("imageDownloadHelper.error.0002", null, imageFilePath);
			}
			image = ImageIO.read(new File(imageFilePath));
		} catch (IOException e) {
			throw new SystemException("imageDownloadHelper.error.0001", e);
		}
		return image;
	}


}
