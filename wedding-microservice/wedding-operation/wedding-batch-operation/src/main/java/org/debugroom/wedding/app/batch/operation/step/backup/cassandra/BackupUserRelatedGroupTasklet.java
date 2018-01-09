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
import org.debugroom.wedding.domain.entity.message.UserRelatedGroup;
import org.debugroom.wedding.domain.repository.cassandra.message.CassandraUserRelatedGroupRepository;

public class BackupUserRelatedGroupTasklet implements Tasklet{

	
	@Inject
	OperationBatchProperties properties;
	
	@Inject
	@Named("backupCassandraWorkHelper")
	BackupWorkHelper backupWorkHelper;

	@Inject
	CassandraUserRelatedGroupRepository userRelatedGroupRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {
		
		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		
		for(UserRelatedGroup userRelatedGroup : userRelatedGroupRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(
					userRelatedGroup.getUserRelatedGrouppk().getUserId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(
					userRelatedGroup.getUserRelatedGrouppk().getGroupId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(userRelatedGroup.getVer()))
			.append(CsvUtil.addSingleQuote(userRelatedGroup.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataCassandraUserRelatedGroup(), data.toString());

		return RepeatStatus.FINISHED;

	}
	
}
