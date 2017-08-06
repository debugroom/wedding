package org.debugroom.wedding.app.batch.gallery.job;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.batch.api.AbstractBatchlet;
import javax.batch.api.Batchlet;

import org.debugroom.wedding.app.batch.common.BatchConsts;
import org.debugroom.wedding.domain.gallery.model.Media;
import org.debugroom.wedding.domain.gallery.model.MediaType;
import org.debugroom.wedding.domain.gallery.service.GalleryService;
import org.debugroom.wedding.domain.model.entity.Photo;
import org.debugroom.wedding.domain.repository.photo.PhotoRepository;

@Named
public class CreateMediaDownloadZipBatchlet extends AbstractBatchlet{

	@Inject
	JobContext jobContext;
	
	@Inject
	GalleryService galleryService;
	
	@Override
	public String process() throws Exception {

		Properties runtimeProperties = BatchRuntime.getJobOperator()
				.getParameters(jobContext.getExecutionId());

		List<String> photoIds = Arrays.asList(runtimeProperties.getProperty(
				BatchConsts.CREATE_MEDIA_DOWNLOAD_PARAMETER_NAME_1).split(
						BatchConsts.CSV_SEPARATOR));

		List<String> movieIds = Arrays.asList(runtimeProperties.getProperty(
				BatchConsts.CREATE_MEDIA_DOWNLOAD_PARAMETER_NAME_2).split(
						BatchConsts.CSV_SEPARATOR));
		
		List<Media> mediaList = new ArrayList<Media>();
		
		for(String photoId : photoIds){
			mediaList.add(Media.builder()
					.mediaId(photoId)
					.mediaType(MediaType.PHOTOGRAPH)
					.build());
		}
		for(String movieId : movieIds){
			mediaList.add(Media.builder()
					.mediaId(movieId)
					.mediaType(MediaType.MOVIE)
					.build());
		}		
		
		Media zipFile = galleryService.createDownloadZipFile(mediaList);

		return null;

	}

}
