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
import org.debugroom.wedding.domain.entity.Menu;
import org.debugroom.wedding.domain.repository.jpa.MenuRepository;

public class BackupMenuTasklet implements Tasklet{

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	@Named("backupPostgresWorkHelper")
	BackupWorkHelper backupWorkHelper;

	@Inject
	MenuRepository menuRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {

		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		
		for(Menu menu : menuRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(menu.getMenuId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(menu.getMenuName()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(menu.getUrl()))
			.append(CsvUtil.addSeparator(menu.hasPathvariables()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(menu.getUsableStartDate()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(menu.getUsableEndDate()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(menu.getAuthorityLevel()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(menu.getVer()))
			.append(CsvUtil.addSingleQuote(menu.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataPostgresMenu(), data.toString());

		return RepeatStatus.FINISHED;
	}
	
}
