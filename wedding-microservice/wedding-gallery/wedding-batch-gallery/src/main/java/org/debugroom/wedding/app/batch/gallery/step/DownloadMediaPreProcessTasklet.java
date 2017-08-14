package org.debugroom.wedding.app.batch.gallery.step;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.debugroom.framework.common.exception.SystemException;
import org.debugroom.wedding.app.batch.gallery.GalleryBatchProperties;
import org.debugroom.wedding.domain.service.common.FileSystemSharedService;
import org.debugroom.wedding.domain.service.gallery.GalleryService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DownloadMediaPreProcessTasklet implements Tasklet{

	@Inject
	GalleryBatchProperties galleryBatchProperties;
	
	@Inject
	FileSystemSharedService fileSystemSharedService;
	
	@Override
	public RepeatStatus execute(StepContribution stepContribution, 
			ChunkContext chunkContext) throws Exception {
		
		log.info(this.getClass().getName() + " starts.");

		StepExecution stepExecution = chunkContext
				.getStepContext()
				.getStepExecution();
		
		ExecutionContext jobExecutionContext = stepExecution
				.getJobExecution()
				.getExecutionContext();
		
		String path = new StringBuilder()
				.append(galleryBatchProperties.getGalleryDownloadWorkRootDirectory())
				.append(java.io.File.separator)
				.append(galleryBatchProperties.getGalleryDownloadDirectory())
				.toString();
		
		String randomString = UUID.randomUUID().toString();
		String downloadDirectoryPath = fileSystemSharedService.createDirectory(
				path, randomString);
		
		String downloadPhotoList = stepExecution.getJobParameters().getString("photoIds");

		if(downloadPhotoList != null){
			
			File downloadPhotoListFile = new File(new StringBuilder()
				.append(downloadDirectoryPath)
				.append(java.io.File.separator)
				.append(galleryBatchProperties.getGalleryDownloadWorkPhotoFilename())
				.toString());

			BufferedWriter downloadPhotoListWriter = null;
			try{
				downloadPhotoListWriter = new BufferedWriter(
						new FileWriter(downloadPhotoListFile));
				downloadPhotoListWriter.write(
					StringUtils.replace(downloadPhotoList, ",",  System.lineSeparator()));
				downloadPhotoListWriter.flush();
			} catch(IOException e){
				throw new SystemException("downloadMediaPreProcessTasklet.error.0001", e);
			}finally{
				downloadPhotoListWriter.close();
			}

			jobExecutionContext.put("downloadPhotoListFilename", new StringBuilder()
				.append("file:").append(downloadPhotoListFile.getAbsolutePath()).toString());

		}

		String downloadMovieList = stepExecution.getJobParameters().getString("movieIds");
		
		if(downloadMovieList != null){
			
			File downloadMovieListFile = new File(new StringBuilder()
				.append(downloadDirectoryPath)
				.append(java.io.File.separator)
				.append(galleryBatchProperties.getGalleryDownloadWorkMovieFilename())
				.toString());
		
			BufferedWriter downloadMovieListWriter = null;
			try{
				downloadMovieListWriter = new BufferedWriter(new FileWriter(downloadMovieListFile));
				downloadMovieListWriter.write(
					StringUtils.replace(downloadMovieList, ",",  System.lineSeparator()));
				downloadMovieListWriter.flush();
			} catch(IOException e){
				throw new SystemException("downloadMediaPreProcessTasklet.error.0001", e);
			}finally{
				downloadMovieListWriter.close();
			}

			jobExecutionContext.put("downloadMovieListFilename", new StringBuilder()
				.append("file:").append(downloadMovieListFile.getAbsolutePath()).toString());
		
		}

		jobExecutionContext.put("accessKey", randomString);
		jobExecutionContext.put("downloadDirectoryPath", downloadDirectoryPath);

		return RepeatStatus.FINISHED;

	}

}
