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
import org.debugroom.wedding.domain.entity.Credential;
import org.debugroom.wedding.domain.repository.jpa.operation.CredentialRepository;

public class BackupCredentialTasklet implements Tasklet{

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	@Named("backupPostgresWorkHelper")
	BackupWorkHelper backupWorkHelper;
	
	@Inject
	CredentialRepository credentialRepository;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {

		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		for(Credential credential : credentialRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(credential.getId().getUserId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(credential.getId().getCredentialType()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(credential.getCredentialKey()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(credential.getValidDate()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(credential.getVer()))
			.append(CsvUtil.addSingleQuote(credential.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataPostgresCredential(), data.toString());
					
		return RepeatStatus.FINISHED;
	}
	
}
