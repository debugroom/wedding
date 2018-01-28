package org.debugroom.wedding.app.web.galley;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import org.debugroom.wedding.domain.common.DomainConsts;
import org.debugroom.wedding.domain.gallery.model.Media;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.framework.common.web.MediaType;

@Component
public class GalleryContentsTypeHelper {

	public Media createMedia(MultipartFile multipartFile) throws BusinessException{
		Media.MediaBuilder mediaBuilder = Media.builder();
		MediaType mediaType = MediaType.getInstance(multipartFile.getContentType());
		if(mediaType == null){
			throw new BusinessException("galleryContentsTypeHelper.error.0001", null, multipartFile.getOriginalFilename());
		}else{
			switch(mediaType){
				case JPEG:
					mediaBuilder
						.extension(DomainConsts.JPEG_EXTENSION)
						.mediaType(org.debugroom.wedding.domain.gallery.model.MediaType.PHOTOGRAPH);
					break;
				case PNG:
					mediaBuilder
						.extension(DomainConsts.PNG_EXTENSION)
						.mediaType(org.debugroom.wedding.domain.gallery.model.MediaType.PHOTOGRAPH);
					break;
				case GIF:
					mediaBuilder
						.extension(DomainConsts.GIF_EXTENSION)
						.mediaType(org.debugroom.wedding.domain.gallery.model.MediaType.PHOTOGRAPH);
					break;
				case TIFF:
					throw new BusinessException("galleryContentsTypeHelper.error.0001", null, multipartFile.getOriginalFilename());
				case BMP:
					mediaBuilder
						.extension(DomainConsts.BMP_EXTENSION)
						.mediaType(org.debugroom.wedding.domain.gallery.model.MediaType.PHOTOGRAPH);
					break;
				case MP4_VIDEO:
					mediaBuilder
						.extension(DomainConsts.MP4_VIDEO_EXTENSION)
						.mediaType(org.debugroom.wedding.domain.gallery.model.MediaType.MOVIE);
					break;
				case WMV:
					mediaBuilder
						.extension(DomainConsts.WMV_VIDEO_EXTENSION)
						.mediaType(org.debugroom.wedding.domain.gallery.model.MediaType.MOVIE);
					break;
				case MPEG_VIDEO:
					mediaBuilder
						.extension(DomainConsts.MPEG_VIDEO_EXTENSION)
						.mediaType(org.debugroom.wedding.domain.gallery.model.MediaType.MOVIE);
					break;
				default:
			}
		}
		return mediaBuilder.build();
	}
}
