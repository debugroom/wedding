package org.debugroom.wedding.app.batch.gallery.step;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.inject.Inject;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.debugroom.wedding.app.batch.gallery.GalleryBatchProperties;
import org.debugroom.wedding.domain.entity.gallery.Photo;
import org.debugroom.wedding.domain.service.gallery.GalleryService;

@Slf4j
@Component
public class CopyPhotoProcessor implements ItemProcessor<Photo, Photo>{

	@Inject
	GalleryBatchProperties galleryBatchProperties;
	
	@Inject
	GalleryService galleryService;

	@Value("#{stepExecution}")
	private StepExecution stepExecution;

	@Override
	public Photo process(Photo photo) throws Exception {
		ExecutionContext stepExecutionContext = stepExecution.getExecutionContext();
		ExecutionContext jobExecutionContext = 
				stepExecution.getJobExecution().getExecutionContext();
		if(photo.getPhotoId().equals(stepExecutionContext.getString("photoId"))){
			Photo target = galleryService.getPhotograph(photo.getPhotoId());
			String srcPath = new StringBuilder()
					.append(galleryBatchProperties.getGalleryRootDirectory())
					.append(java.io.File.separator)
					.append(target.getFilePath())
					.toString();
			String destPath = new StringBuilder()
					.append(jobExecutionContext.getString("downloadDirectoryPath"))
					.append(java.io.File.separator)
					.append("photo-")
					.append(target.getPhotoId())
					.append(".")
					.append(StringUtils.substringAfter(target.getFilePath(), "."))
					.toString();
			
	        try {
	        	StopWatch stopWatch = new StopWatch();
	        	stopWatch.start();
	            Files.copy(Paths.get(srcPath),
	                       Paths.get(destPath),
	                       StandardCopyOption.REPLACE_EXISTING);
	            stopWatch.stop();
	            log.info(this.getClass().getName() + " starts. photoId : " 
	            + target.getPhotoId() + " : " + stopWatch.prettyPrint());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			return target;
		}
		return null;
	}

}
