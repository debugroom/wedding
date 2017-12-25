package org.debugroom.wedding.app.batch.operation.step;

import javax.inject.Inject;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import org.debugroom.wedding.app.batch.operation.OperationBatchProperties;
import org.debugroom.wedding.app.batch.operation.helper.BackupWorkHelper;
import org.debugroom.wedding.common.util.CsvUtil;
import org.debugroom.wedding.domain.entity.Information;
import org.debugroom.wedding.domain.repository.jpa.InformationRepository;

public class BackupInformationTasklet implements Tasklet{

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	BackupWorkHelper backupWorkHelper;
	
	@Inject
	InformationRepository informationRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {
		
		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		for(Information information : informationRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(information.getInfoId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(information.getTitle()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(information.getInfoPagePath()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(information.getRegistratedDate()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(information.getReleaseDate()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(information.getVer()))
			.append(CsvUtil.addSingleQuote(information.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataPostgresInformation(), data.toString());

		return RepeatStatus.FINISHED;
	}

}
