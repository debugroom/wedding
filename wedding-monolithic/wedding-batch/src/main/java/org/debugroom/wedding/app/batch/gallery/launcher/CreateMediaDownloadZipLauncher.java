package org.debugroom.wedding.app.batch.gallery.launcher;

import java.util.List;
import java.util.Properties;

import javax.inject.Named;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;

import org.debugroom.wedding.app.batch.common.BatchConsts;
import org.debugroom.wedding.domain.gallery.model.Media;
import org.debugroom.wedding.domain.gallery.model.MediaType;

@Named
public class CreateMediaDownloadZipLauncher {

	public static final String JOB_ID = BatchConsts.CREATE_MEDIA_DOWNLOAD_ZIP_JOB_ID;
	public static final String CSV_SEPARATOR = BatchConsts.CSV_SEPARATOR;

	public void start(List<Media> mediaList){
		
		Properties properties = new Properties();
		properties.setProperty(BatchConsts.CREATE_MEDIA_DOWNLOAD_PARAMETER_NAME_1, 
				createBatchParameters(MediaType.PHOTOGRAPH, mediaList));
		properties.setProperty(BatchConsts.CREATE_MEDIA_DOWNLOAD_PARAMETER_NAME_2, 
				createBatchParameters(MediaType.MOVIE, mediaList));
		
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		long executionId = jobOperator.start(JOB_ID, properties);
	}
	
	private String createBatchParameters(MediaType mediaType, List<Media> mediaList){
		StringBuilder stringBuilder = new StringBuilder();
		boolean isFirstElement = true;
		for(Media media : mediaList){
			switch (mediaType) {
				case PHOTOGRAPH:
					if(media.getMediaType() == MediaType.PHOTOGRAPH){
						if(!isFirstElement){
							stringBuilder.append(CSV_SEPARATOR);
						}
						stringBuilder.append(media.getMediaId());
						isFirstElement = false;
					}
					break;
				case MOVIE:
					if(media.getMediaType() == MediaType.MOVIE){
						if(!isFirstElement){
							stringBuilder.append(CSV_SEPARATOR);
						}
						stringBuilder.append(media.getMediaId());
						isFirstElement = false;
					}
					break;
				default:
					break;
			}
		}
		return stringBuilder.toString();
	}

}
