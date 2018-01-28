package org.debugroom.wedding.app.batch.operation.step.backup.postgres;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import org.debugroom.wedding.app.batch.operation.OperationBatchProperties;
import org.debugroom.wedding.app.batch.operation.helper.BackupWorkHelper;
import org.debugroom.wedding.common.util.CsvUtil;
import org.debugroom.wedding.domain.entity.operation.MovieRelatedUser;
import org.debugroom.wedding.domain.repository.jpa.operation.MovieRelatedUserRepository;

public class BackupMovieRelatedUserTasklet implements Tasklet {

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	@Named("backupPostgresWorkHelper")
	BackupWorkHelper backupWorkHelper;
	
	@Inject
	MovieRelatedUserRepository movieRelatedUserRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
		throws Exception {
		
		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		for(MovieRelatedUser movieRelatedUser : movieRelatedUserRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(movieRelatedUser.getId().getMovieId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(movieRelatedUser.getId().getUserId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(movieRelatedUser.getVer()))
			.append(CsvUtil.addSingleQuote(movieRelatedUser.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataPostgresMovieRelatedUser(), data.toString());
		return RepeatStatus.FINISHED;

	}

}
