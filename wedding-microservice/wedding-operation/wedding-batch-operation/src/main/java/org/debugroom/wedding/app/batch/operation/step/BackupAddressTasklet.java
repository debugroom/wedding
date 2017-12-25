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
import org.debugroom.wedding.domain.entity.Address;
import org.debugroom.wedding.domain.repository.jpa.AddressRepository;

public class BackupAddressTasklet implements Tasklet{

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	BackupWorkHelper backupWorkHelper;
	
	@Inject
	AddressRepository addressRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {

		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		
		for(Address address : addressRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(address.getUserId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(address.getPostCd()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(address.getAddress()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(address.getVer()))
			.append(CsvUtil.addSingleQuote(address.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
			
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataPostgresAddress(), data.toString());

		return RepeatStatus.FINISHED;

	}


}
