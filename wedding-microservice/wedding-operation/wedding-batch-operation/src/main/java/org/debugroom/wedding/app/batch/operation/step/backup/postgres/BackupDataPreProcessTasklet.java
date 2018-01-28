package org.debugroom.wedding.app.batch.operation.step.backup.postgres;

import javax.inject.Inject;
import javax.inject.Named;

import org.debugroom.wedding.app.batch.operation.helper.BackupWorkHelper;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

public class BackupDataPreProcessTasklet implements Tasklet{

	@Inject
	@Named("backupPostgresWorkHelper")
	BackupWorkHelper backupWorkHelper;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, 
			ChunkContext chunkContext) throws Exception {

		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = backupWorkHelper.createBackupDirectory();
		
		executionContext.put("backupDirectoryPath", backupDirectoryPath);

		return RepeatStatus.FINISHED;
	}

}
