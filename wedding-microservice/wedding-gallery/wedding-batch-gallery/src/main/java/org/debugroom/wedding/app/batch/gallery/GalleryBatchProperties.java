package org.debugroom.wedding.app.batch.gallery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class GalleryBatchProperties {

	@Value("${gallery.download.work.root.directory}")
	private String galleryDownloadWorkRootDirectory;
	
	@Value("${gallery.download.work.directory}")
	private String galleryDownloadDirectory;
	
	@Value("${gallery.download.work.photo.filename}")
	private String galleryDownloadWorkPhotoFilename;

	@Value("${gallery.download.work.movie.filename}")
	private String galleryDownloadWorkMovieFilename;
	
	@Value("${gallery.root.directory}")
	private String galleryRootDirectory;
	
	@Value("${gallery.download.filename}")
	private String galleryDownloadFilename;

}
