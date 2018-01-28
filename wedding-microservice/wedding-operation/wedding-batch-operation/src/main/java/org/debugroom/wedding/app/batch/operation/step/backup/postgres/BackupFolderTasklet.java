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
import org.debugroom.wedding.domain.entity.operation.Folder;
import org.debugroom.wedding.domain.repository.jpa.operation.FolderRepository;

public class BackupFolderTasklet implements Tasklet{

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	@Named("backupPostgresWorkHelper")
	BackupWorkHelper backupWorkHelper;
	
	@Inject
	FolderRepository folderRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
			throws Exception {
		
		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		for(Folder folder : folderRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(folder.getFolderId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(folder.getFolderName()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(folder.getParentFolderId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(folder.getVer()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(folder.getLastUpdatedDate()))
			.append(CsvUtil.addSingleQuote(folder.getUsr().getUserId()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataPostgresFolder(), data.toString());

		return RepeatStatus.FINISHED;

	}

}
