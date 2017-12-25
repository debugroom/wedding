package org.debugroom.wedding.app.batch.operation.step;

import javax.inject.Inject;

import org.debugroom.wedding.app.batch.operation.OperationBatchProperties;
import org.debugroom.wedding.app.batch.operation.helper.BackupWorkHelper;
import org.debugroom.wedding.common.util.CsvUtil;
import org.debugroom.wedding.domain.entity.operation.PhotoRelatedFolder;
import org.debugroom.wedding.domain.repository.jpa.operation.PhotoRelatedFolderRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

public class BackupPhotoRelatedFolderTasklet  implements Tasklet{

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	BackupWorkHelper backupWorkHelper;
	
	@Inject
	PhotoRelatedFolderRepository photoRelatedFolderRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {
		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		for(PhotoRelatedFolder photoRelatedFolder : photoRelatedFolderRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(photoRelatedFolder.getId().getPhotoId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(photoRelatedFolder.getId().getFolderId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(photoRelatedFolder.getVer()))
			.append(CsvUtil.addSingleQuote(photoRelatedFolder.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataPostgresPhotoRelatedFolder(), data.toString());

		return RepeatStatus.FINISHED;

	}

}
