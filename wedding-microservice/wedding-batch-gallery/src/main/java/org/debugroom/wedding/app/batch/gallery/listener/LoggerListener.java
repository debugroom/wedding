package org.debugroom.wedding.app.batch.gallery.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerListener extends JobExecutionListenerSupport {@Override
	public void afterJob(JobExecution jobExecution) {
		log.info(this.getClass().getName() + "#after() was called.");
		super.afterJob(jobExecution);
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info(this.getClass().getName() + "#before() was called.");
		super.beforeJob(jobExecution);
	}

}
