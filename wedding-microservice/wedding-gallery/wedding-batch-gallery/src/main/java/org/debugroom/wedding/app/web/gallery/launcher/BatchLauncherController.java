package org.debugroom.wedding.app.web.gallery.launcher;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.debugroom.wedding.app.batch.gallery.model.DownloadMedia;
import org.debugroom.wedding.app.web.gallery.helper.BatchArgsCreateHelper;
import org.debugroom.wedding.domain.service.common.DateUtil;

@RestController
@RequestMapping("/api/v1")
public class BatchLauncherController {

	@Inject
	JobLauncher jobLauncher;
	
	@Inject
	BatchArgsCreateHelper batchArgsCreateHelper;
	
	@Inject
	Job job;
	
	@RequestMapping(value="/gallery/archive", method=RequestMethod.POST)
	public String createArchiveBatch(@RequestBody DownloadMedia downloadMedia) 
			throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException{
		Map<String, JobParameter> map = new HashMap<String, JobParameter>();
		String photoIdsArg = batchArgsCreateHelper.getPhotoArgs(downloadMedia);
		String movieIdsArg = batchArgsCreateHelper.getMovieArgs(downloadMedia);
		if(!"".equals(photoIdsArg)){
			map.put("photoIds", new JobParameter(photoIdsArg));
		}
		if(!"".equals(movieIdsArg)){
			map.put("movieIds", new JobParameter(movieIdsArg));
		}
		map.put("time", new JobParameter(DateUtil.getCurrentDate()));
		JobParameters jobParameters = new JobParameters(map);
		JobExecution jobExecution = jobLauncher.run(job, jobParameters);
		ExecutionContext jobExecutionContext = jobExecution.getExecutionContext();
		return jobExecutionContext.getString("accessKey");
	}

}
