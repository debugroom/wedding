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
import org.debugroom.wedding.domain.entity.Email;
import org.debugroom.wedding.domain.repository.jpa.operation.EmailRepository;

public class BackupEmailTasklet implements Tasklet{

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	BackupWorkHelper backupWorkHelper;

	@Inject
	EmailRepository emailRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {

		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		for(Email email: emailRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(email.getId().getUserId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(email.getId().getEmailId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(email.getEmail()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(email.getVer()))
			.append(CsvUtil.addSingleQuote(email.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataPostgresEmail(), data.toString());
		return RepeatStatus.FINISHED;
	}
	
}
