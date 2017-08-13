package org.debugroom.wedding.app.web.gallery.helper;

import java.io.File;

import javax.inject.Inject;

import org.debugroom.wedding.app.batch.gallery.GalleryBatchProperties;
import org.springframework.stereotype.Component;

@Component
public class DownloadHelper {

	@Inject
	GalleryBatchProperties galleryBatchProperties;
	
	public File getDownloadFile(String accessKey){
		return new File(new StringBuilder()
				.append(galleryBatchProperties.getGalleryDownloadWorkRootDirectory())
				.append(java.io.File.separator)
				.append(galleryBatchProperties.getGalleryDownloadDirectory())
				.append(java.io.File.separator)
				.append(accessKey)
				.append(java.io.File.separator)
				.append(galleryBatchProperties.getGalleryDownloadFilename())
				.toString());
	}
}
