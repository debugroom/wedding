package org.debugroom.wedding.app.web.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.framework.common.exception.SystemException;
import org.debugroom.wedding.domain.entity.User;

@Component
public class ImageDownloadHelper {

	@Value("${image.download.root.directory}")
	private String imageDownloadRootDirectory;
	
	private static final String FILE_SEPALATOR = System.getProperty("file.separator");
	
	public BufferedImage getProfileImage(User user) throws BusinessException{
		String rootPath = new StringBuilder()
									.append(imageDownloadRootDirectory)
									.toString();
		String imageFilePath = new StringBuilder()
									.append(rootPath)
									.append(FILE_SEPALATOR)
									.append(user.getImageFilePath())
									.toString();
		BufferedImage image = null;
		try {
			// ディレクトリトラバーサル対策：指定されたファイルパスが、イメージを保存しているルートパス配下にあるか確認する。
			if(!StringUtils.startsWith(new File(imageFilePath).getCanonicalPath(), rootPath)){
				throw new BusinessException("imageDownloadHelper.error.0002", null, user.getUserId());
			}
			image = ImageIO.read(new File(imageFilePath));
		} catch (IOException e) {
			throw new SystemException("imageDownloadHelper.error.0001", e);
		}
		return image;
	}
	
}
