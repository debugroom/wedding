package org.debugroom.wedding.app.batch.operation.step.backup.cassandra;

import javax.inject.Inject;
import javax.inject.Named;

import org.debugroom.wedding.app.batch.operation.OperationBatchProperties;
import org.debugroom.wedding.app.batch.operation.helper.BackupWorkHelper;
import org.debugroom.wedding.common.util.CsvUtil;
import org.debugroom.wedding.domain.entity.message.GroupRelatedUser;
import org.debugroom.wedding.domain.repository.cassandra.message.CassandraGroupRelatedUserRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

public class BackupGroupRelatedUserTasklet implements Tasklet{

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	@Named("backupCassandraWorkHelper")
	BackupWorkHelper backupWorkHelper;

	@Inject
	CassandraGroupRelatedUserRepository groupRelatedUserRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {
		
		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		
		for(GroupRelatedUser groupRelatedUser : groupRelatedUserRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(
					groupRelatedUser.getGroupRelatedUserpk().getGroupId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(
					groupRelatedUser.getGroupRelatedUserpk().getUserId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(groupRelatedUser.getVer()))
			.append(CsvUtil.addSingleQuote(groupRelatedUser.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataCassandraGroupRelatedUser(), data.toString());

		return RepeatStatus.FINISHED;

	}

}
