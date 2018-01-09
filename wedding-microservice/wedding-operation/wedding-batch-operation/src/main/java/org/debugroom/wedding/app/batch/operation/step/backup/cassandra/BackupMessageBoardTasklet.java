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
import org.debugroom.wedding.domain.entity.message.MessageBoard;
import org.debugroom.wedding.domain.repository.cassandra.message.CassandraMessageBoardRepository;

public class BackupMessageBoardTasklet implements Tasklet{

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	@Named("backupCassandraWorkHelper")
	BackupWorkHelper backupWorkHelper;

	@Inject
	CassandraMessageBoardRepository messageBoardRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {
		
		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		for(MessageBoard messageBoard : messageBoardRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(
					messageBoard.getMessageBoardId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(messageBoard.getTitle()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(messageBoard.getLastUpdatedDate()))
			.append(CsvUtil.addSingleQuote(messageBoard.getVer()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataCassandraMessageBoard(), data.toString());

		return RepeatStatus.FINISHED;

	}
}
