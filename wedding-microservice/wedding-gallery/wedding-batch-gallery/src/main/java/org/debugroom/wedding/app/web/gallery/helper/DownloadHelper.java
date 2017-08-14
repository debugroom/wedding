package org.debugroom.wedding.app.web.gallery.helper;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.batch.gallery.GalleryBatchProperties;
import org.springframework.stereotype.Component;

@Component
public class DownloadHelper {

	@Inject
	GalleryBatchProperties galleryBatchProperties;
	
	public File getDownloadFile(String accessKey) 
			throws BusinessException, IOException{
		File downloadFile = new File(new StringBuilder()
				.append(galleryBatchProperties.getGalleryDownloadWorkRootDirectory())
				.append(java.io.File.separator)
				.append(galleryBatchProperties.getGalleryDownloadDirectory())
				.append(java.io.File.separator)
				.append(accessKey)
				.append(java.io.File.separator)
				.append(galleryBatchProperties.getGalleryDownloadFilename())
				.toString());
		
		// ディレクトリトラバーサル対策
		if(!StringUtils.startsWith(downloadFile.getCanonicalPath(), 
				galleryBatchProperties.getGalleryDownloadWorkRootDirectory())){
			throw new BusinessException("downloadHelper.error.0001", null, 
					downloadFile.getCanonicalFile());
		}
		return downloadFile;
	}
}
