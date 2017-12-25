package org.debugroom.wedding.app.batch.operation.step;

import javax.inject.Inject;

import org.debugroom.wedding.app.batch.operation.OperationBatchProperties;
import org.debugroom.wedding.app.batch.operation.helper.BackupWorkHelper;
import org.debugroom.wedding.common.util.CsvUtil;
import org.debugroom.wedding.domain.entity.Notification;
import org.debugroom.wedding.domain.repository.jpa.NotificationRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

public class BackupNotificationTasklet implements Tasklet {

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	BackupWorkHelper backupWorkHelper;
	
	@Inject
	NotificationRepository notificationRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {
		
		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		for(Notification notification : notificationRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(notification.getId().getInfoId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(notification.getId().getUserId()))
			.append(CsvUtil.addSeparator(notification.getIsAccessed()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(notification.getVer()))
			.append(CsvUtil.addSingleQuote(notification.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataPostgresNotification(), data.toString());

		return RepeatStatus.FINISHED;

	}

}
