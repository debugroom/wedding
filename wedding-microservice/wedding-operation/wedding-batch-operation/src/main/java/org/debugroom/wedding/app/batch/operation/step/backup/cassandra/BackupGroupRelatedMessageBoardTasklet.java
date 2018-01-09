package org.debugroom.wedding.app.batch.operation.step.backup.cassandra;

import javax.inject.Inject;
import javax.inject.Named;

import org.debugroom.wedding.app.batch.operation.OperationBatchProperties;
import org.debugroom.wedding.app.batch.operation.helper.BackupWorkHelper;
import org.debugroom.wedding.common.util.CsvUtil;
import org.debugroom.wedding.domain.entity.message.GroupRelatedMessageBoard;
import org.debugroom.wedding.domain.repository.cassandra.message.CassandraGroupRelatedMessageBoardRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

public class BackupGroupRelatedMessageBoardTasklet implements Tasklet{

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	@Named("backupCassandraWorkHelper")
	BackupWorkHelper backupWorkHelper;
	
	@Inject
	CassandraGroupRelatedMessageBoardRepository groupRelatedMessageBoardRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {

		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");
				
		StringBuilder data = new StringBuilder();
		
		for(GroupRelatedMessageBoard groupRelatedMessageBoard :
			groupRelatedMessageBoardRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(
					groupRelatedMessageBoard.getGroupRelatedMessageBoardpk().getGroupId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(
					groupRelatedMessageBoard.getGroupRelatedMessageBoardpk().getMessageBoardId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(
					groupRelatedMessageBoard.getVer()))
			.append(CsvUtil.addSingleQuote(groupRelatedMessageBoard.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
		}

		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataCassandraGroupRelatedMessageBoard(), data.toString());

		return RepeatStatus.FINISHED;

	}

}
