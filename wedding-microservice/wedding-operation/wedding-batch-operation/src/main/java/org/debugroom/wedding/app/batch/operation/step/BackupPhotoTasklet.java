package org.debugroom.wedding.app.batch.operation.step;

import javax.inject.Inject;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import org.debugroom.wedding.app.batch.operation.OperationBatchProperties;
import org.debugroom.wedding.app.batch.operation.helper.BackupWorkHelper;
import org.debugroom.wedding.common.util.CsvUtil;
import org.debugroom.wedding.domain.entity.operation.Photo;
import org.debugroom.wedding.domain.repository.jpa.operation.PhotoRepository;

public class BackupPhotoTasklet implements Tasklet {

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	BackupWorkHelper backupWorkHelper;
	
	@Inject
	PhotoRepository photoRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {

		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		for(Photo photo : photoRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(photo.getPhotoId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(photo.getFilePath()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(photo.getThumbnailFilePath()))
			.append(CsvUtil.addSeparator(photo.getIsControled()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(photo.getVer()))
			.append(CsvUtil.addSingleQuote(photo.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataPostgresPhoto(), data.toString());

		return RepeatStatus.FINISHED;

	}

}
