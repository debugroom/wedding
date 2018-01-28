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
import org.debugroom.wedding.domain.entity.operation.Movie;
import org.debugroom.wedding.domain.repository.jpa.operation.MovieRepository;

public class BackupMovieTasklet implements Tasklet {

	@Inject
	OperationBatchProperties properties;
	
	@Inject
	@Named("backupPostgresWorkHelper")
	BackupWorkHelper backupWorkHelper;
	
	@Inject
	MovieRepository movieRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
			throws Exception {

		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
		
		String backupDirectoryPath = executionContext.getString("backupDirectoryPath");

		StringBuilder data = new StringBuilder();
		
		for(Movie movie : movieRepository.findAll()){
			data.append(CsvUtil.addSingleQuoteAndWhiteSpace(movie.getMovieId()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(movie.getFilePath()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(movie.getThumbnailFilePath()))
			.append(CsvUtil.addSeparator(movie.getIsControled()))
			.append(CsvUtil.addSingleQuoteAndWhiteSpace(movie.getVer()))
			.append(CsvUtil.addSingleQuote(movie.getLastUpdatedDate()))
			.append(System.getProperty("line.separator"));
		}
		backupWorkHelper.save(backupDirectoryPath, 
				properties.getOperationBackupDataPostgresMovie(), data.toString());

		return RepeatStatus.FINISHED;
	}

}
