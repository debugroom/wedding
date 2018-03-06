package org.debugroom.wedding.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.SimplePartitioner;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.debugroom.wedding.app.batch.gallery.listener.LoggerListener;
import org.debugroom.wedding.app.batch.gallery.partitioner.PhotoPartitioner;
import org.debugroom.wedding.app.batch.gallery.step.CopyPhotoProcessor;
import org.debugroom.wedding.app.batch.gallery.step.CreateZipWriter;
import org.debugroom.wedding.app.batch.gallery.step.DownloadMediaPreProcessTasklet;
import org.debugroom.wedding.domain.entity.gallery.Photo;

@ComponentScan({"org.debugroom.wedding.config",
	"org.debugroom.wedding.app.batch.gallery"
	})
@Configuration
@EnableBatchProcessing
public class BatchAppConfig extends DefaultBatchConfigurer{

    @Bean
    public TaskExecutor asyncTaskExecutor(){
    	SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
    	simpleAsyncTaskExecutor.setConcurrencyLimit(10);
    	return simpleAsyncTaskExecutor;
    }

	@Bean
	public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(
        	"i18n/batch-gallery-system-messages"
        );
        return messageSource;
	}
	
    @Override
    @Autowired
    public void setDataSource(@Qualifier("batchDataSource") DataSource dataSource){
    	super.setDataSource(dataSource);
    }
    
}
