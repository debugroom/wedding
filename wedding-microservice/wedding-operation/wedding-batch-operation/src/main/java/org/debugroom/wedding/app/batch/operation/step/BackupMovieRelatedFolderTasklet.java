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
import org.debugroom.wedding.domain.entity.operation.MovieRelatedFolder;
import org.debugroom.wedding.domain.repository.jpa.operation.MovieRelatedFolderRepository;

public class BackupMovieRelatedFolderTasklet implements Tasklet {

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	BackupWorkHelper backupWorkHelper;
	
	@Inject
	MovieRelatedFolderRepository movieRelatedFolderRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
			throws Exception {
		
		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		for(MovieRelatedFolder movieRelatedFolder : movieRelatedFolderRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(movieRelatedFolder.getId().getMovieId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(movieRelatedFolder.getId().getFolderId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(movieRelatedFolder.getVer()))
			.append(CsvUtil.addSingleQuote(movieRelatedFolder.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataPostgresMovieRelatedFolder(), data.toString());

		return RepeatStatus.FINISHED;

	}

}
