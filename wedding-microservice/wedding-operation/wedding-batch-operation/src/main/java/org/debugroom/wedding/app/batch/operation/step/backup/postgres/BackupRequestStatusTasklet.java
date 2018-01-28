package org.debugroom.wedding.app.batch.operation.step.backup.postgres;

import javax.inject.Inject;
import javax.inject.Named;

import org.debugroom.wedding.app.batch.operation.OperationBatchProperties;
import org.debugroom.wedding.app.batch.operation.helper.BackupWorkHelper;
import org.debugroom.wedding.common.util.CsvUtil;
import org.debugroom.wedding.domain.entity.operation.RequestStatus;
import org.debugroom.wedding.domain.repository.jpa.operation.RequestStatusRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

public class BackupRequestStatusTasklet implements Tasklet {

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	@Named("backupPostgresWorkHelper")
	BackupWorkHelper backupWorkHelper;
	
	@Inject
	RequestStatusRepository requestStatusRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {
		
		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		for(RequestStatus requestStatus : requestStatusRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(requestStatus.getId().getRequestId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(requestStatus.getId().getUserId()))
			.append(CsvUtil.addSeparator(requestStatus.getIsAnswered()))
			.append(CsvUtil.addSeparator(requestStatus.getIsApproved()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(requestStatus.getResponse()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(requestStatus.getVer()))
			.append(CsvUtil.addSingleQuote(requestStatus.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataPostgresRequestStatus(), data.toString());

		return RepeatStatus.FINISHED;

	}

}
