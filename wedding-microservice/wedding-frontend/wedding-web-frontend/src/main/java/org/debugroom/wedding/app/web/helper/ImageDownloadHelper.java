package org.debugroom.wedding.app.web.helper;

import java.awt.image.BufferedImage;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.model.gallery.Movie;
import org.debugroom.wedding.app.model.gallery.Photo;
import org.debugroom.wedding.domain.entity.User;
import org.springframework.http.HttpHeaders;

public interface ImageDownloadHelper {

	public BufferedImage getProfileImage(User user) throws BusinessException;
	public String getMediaType(String fileName);
	public HttpHeaders getHeaders(Photo photo);
	public BufferedImage getGalleryThumbnailImage(Photo photo) throws BusinessException;
	public BufferedImage getGalleryThumbnailImage(Movie movie) throws BusinessException;
	public BufferedImage getGalleryImage(Photo photo) throws BusinessException;
	
}
