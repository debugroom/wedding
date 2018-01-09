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
import org.debugroom.wedding.domain.entity.Function;
import org.debugroom.wedding.domain.repository.jpa.operation.FunctionRepository;

public class BackupFunctionTasklet implements Tasklet{

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	@Named("backupPostgresWorkHelper")
	BackupWorkHelper backupWorkHelper;

	@Inject
	FunctionRepository functionRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {
		
		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		
		for(Function function: functionRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(function.getId().getMenuId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(function.getId().getFunctionId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(function.getFunctionName()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(function.getUrl()))
			.append(CsvUtil.addSeparator(function.hasPathvariables()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(function.getUsableStartDate()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(function.getUsableEndDate()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(function.getAuthorityLevel()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(function.getVer()))
			.append(CsvUtil.addSingleQuote(function.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataPostgresFunction(), data.toString());
		return RepeatStatus.FINISHED;

	}
	
}
