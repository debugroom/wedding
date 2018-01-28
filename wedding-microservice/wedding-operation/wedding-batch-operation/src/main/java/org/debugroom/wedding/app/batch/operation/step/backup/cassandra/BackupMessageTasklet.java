package org.debugroom.wedding.app.batch.operation.step.backup.cassandra;

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
import org.debugroom.wedding.domain.entity.message.Message;
import org.debugroom.wedding.domain.repository.cassandra.message.CassandraMessageRepository;

public class BackupMessageTasklet implements Tasklet{

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	@Named("backupCassandraWorkHelper")
	BackupWorkHelper backupWorkHelper;

	@Inject
	CassandraMessageRepository messageRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {
		
		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();

		for(Message message : messageRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(
					message.getMessagepk().getMessageBoardId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(
					message.getMessagepk().getMessageNo()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(message.getComment()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(message.getLastUpdatedDate()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(message.getMovie()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(message.getPhoto()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(message.getUserId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(message.getLikeCount()))
			.append(CsvUtil.addSingleQuote(message.getVer()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataCassandraMessage(), data.toString());

		return RepeatStatus.FINISHED;

	}
	
}
