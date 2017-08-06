package org.debugroom.wedding.domain.service.gallery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class GalleryDomainProperties {

	@Value("${number.of.photo.for.gallery}")
	private int numberOfPhotoForGallery;

}
