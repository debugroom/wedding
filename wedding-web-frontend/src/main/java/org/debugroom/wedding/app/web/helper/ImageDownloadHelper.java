package org.debugroom.wedding.app.web.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.debugroom.framework.common.exception.SystemException;
import org.debugroom.wedding.domain.entity.User;

@Component
public class ImageDownloadHelper {

	@Value("${image.download.root.directory}")
	private String imageDownloadRootDirectory;
	
	private static final String FILE_SEPALATOR = System.getProperty("file.separator");
	
	public BufferedImage getProfileImage(User user){
		String imageFilePath = new StringBuilder()
									.append(imageDownloadRootDirectory)
									.append(FILE_SEPALATOR)
									.append(user.getImageFilePath())
									.toString();
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(imageFilePath));
		} catch (IOException e) {
			throw new SystemException("imageDownloadHelper.error.0001", e);
		}
		return image;
	}
	
}
