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
}
