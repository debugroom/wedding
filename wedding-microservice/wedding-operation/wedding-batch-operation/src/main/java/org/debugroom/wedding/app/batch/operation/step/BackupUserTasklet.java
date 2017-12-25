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
import org.debugroom.wedding.domain.entity.operation.User;
import org.debugroom.wedding.domain.repository.jpa.operation.OperationUserRepository;

public class BackupUserTasklet implements Tasklet{

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	BackupWorkHelper backupWorkHelper;
	
	@Inject
	OperationUserRepository userRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {
		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		for(User user : userRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(user.getUserId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(user.getLastName()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(user.getFirstName()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(user.getLoginId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(user.getImageFilePath()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(user.getAuthorityLevel()))
			.append(CsvUtil.addSeparator(user.getIsBrideSide()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(user.getLastLoginDate()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(user.getVer()))
			.append(CsvUtil.addSingleQuote(user.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataPostgresUser(), data.toString());
		
		return RepeatStatus.FINISHED;
	}

}
