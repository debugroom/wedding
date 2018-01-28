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
import org.debugroom.wedding.domain.entity.operation.GroupFolder;
import org.debugroom.wedding.domain.repository.jpa.operation.GroupFolderRepository;

public class BackupGroupFolderTasklet implements Tasklet{

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	@Named("backupPostgresWorkHelper")
	BackupWorkHelper backupWorkHelper;

	@Inject
	GroupFolderRepository groupFolderRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {
		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		for(GroupFolder groupFolder : groupFolderRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(groupFolder.getId().getFolderId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(groupFolder.getId().getGroupId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(groupFolder.getVer()))
			.append(CsvUtil.addSingleQuote(groupFolder.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataPostgresGroupFolder(), data.toString());

		return RepeatStatus.FINISHED;
		
	}

}
