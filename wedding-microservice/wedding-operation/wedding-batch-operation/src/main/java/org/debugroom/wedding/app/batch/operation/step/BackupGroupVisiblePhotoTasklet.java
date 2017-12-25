package org.debugroom.wedding.app.batch.operation.step;

import javax.inject.Inject;

import org.debugroom.wedding.app.batch.operation.OperationBatchProperties;
import org.debugroom.wedding.app.batch.operation.helper.BackupWorkHelper;
import org.debugroom.wedding.common.util.CsvUtil;
import org.debugroom.wedding.domain.entity.operation.GroupVisiblePhoto;
import org.debugroom.wedding.domain.repository.jpa.operation.GroupVisiblePhotoRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

public class BackupGroupVisiblePhotoTasklet implements Tasklet{

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	BackupWorkHelper backupWorkHelper;
	
	@Inject
	GroupVisiblePhotoRepository groupVisiblePhotoRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {
		
		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		for(GroupVisiblePhoto groupVisiblePhoto : groupVisiblePhotoRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(groupVisiblePhoto.getId().getGroupId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(groupVisiblePhoto.getId().getPhotoId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(groupVisiblePhoto.getVer()))
			.append(CsvUtil.addSingleQuote(groupVisiblePhoto.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataPostgresGroupVisiblePhoto(), data.toString());

		return RepeatStatus.FINISHED;

	}

}
