package org.debugroom.wedding.domain.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class DomainProperties {

	@Value("${credential.type.password}")
	private String credentialTypePassword;

	@Value("${password.expired.day.default}")
	private String passwordExpiredDayDefault;

	@Value("${info.rootpath}")
	private String infoRootPath;

	@Value("${gallery.numberOfPhoto}")
	private int numberOfPhotoForGallery;
	
	@Value("${gallery.image.rootdirectory}")
	private String galleryImageRootDirectory;
	
	@Value("${gallery.image.original.directory}")
	private String galleryImageOriginalDirectory;
	
	@Value("${gallery.image.thumbnail.directory}")
	private String galleryImageThumbnailDirectory;
	
	@Value("${gallery.image.thumbnail.width}")
	private int galleryImageThumbnailWidth;

	@Value("${gallery.image.thumbnail.height}")
	private int galleryImageThumbnailHeight;
	
	@Value("${gallery.movie.rootdirectory}")
	private String galleryMovieRootDirectory;
	
	@Value("${gallery.movie.original.directory}")
	private String galleryMovieOriginalDirectory;
	
	@Value("${gallery.movie.thumbnail.directory}")
	private String galleryMovieThumbnailDirectory;
	
	@Value("${gallery.movie.thumbnail.width}")
	private int galleryMovieThumbnailWidth;

	@Value("${gallery.movie.thumbnail.height}")
	private int galleryMovieThumbnailHeight;

	@Value("${gallery.download.directory.name}")
	private String galleryDownloadDirectoryName;
	
	@Value("${gallery.download.command}")
	private String galleryDownloadCommand;

	@Value("${gallery.download.command.path}")
	private String galleryDownloadCommandPath;

	@Value("${gallery.download.command.copy.media}")
	private String galleryDownloadCommandCopyMedia;

	@Value("${gallery.download.command.create.zip}")
	private String galleryDownloadCommandCreateZip;

	@Value("${gallery.download.zip.file.pattern}")
	private String galleryDownloadZipFilePattern;
	
}
