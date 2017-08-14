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

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job(@Qualifier("step1") Step step1, @Qualifier("step2") Step step2) {
        return jobBuilderFactory
        		.get("job")
        		.listener(jobExecutionListener())
        		.start(step1)
        		.next(partitionStep())
        		.build();
    }

    @Bean
    protected Step step1(Tasklet tasklet) {
        return stepBuilderFactory.get("step1")
            .tasklet(downloadMediaPreProcessTasklet())
            .build();
    }   
    
    @Bean
    protected Step partitionStep(){
    	return stepBuilderFactory.get("partitionStep")
    			.partitioner(step2())
    			.partitioner("step2", partitioner(null))
    			.taskExecutor(taskExecutor())
    			.build();
    }

    @Bean
    protected Step step2() {
        return stepBuilderFactory.get("step2")
        	.<Photo, Photo>chunk(100)
        	.reader(photoItemReader(null))
        	.processor(copyPhotoProcessor())
        	.writer(createZipWriter())
            .build();
    }

    @Bean
    @StepScope
    @Value("#{jobExecutionContext['downloadPhotoListFilename']}")
    public FlatFileItemReader<Photo> photoItemReader(String downloadPhotoListFilename){
    	FlatFileItemReader<Photo> flatFileItemReader = new FlatFileItemReader<Photo>();
    	flatFileItemReader.setResource(
    			new DefaultResourceLoader().getResource(downloadPhotoListFilename));

    	DefaultLineMapper<Photo> defaultLineMapper = new DefaultLineMapper<Photo>();
    	DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
    	delimitedLineTokenizer.setNames(new String[]{"photoId"});
    	defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
    	
    	BeanWrapperFieldSetMapper<Photo> beanWrapperFieldSetMapper =
    			new BeanWrapperFieldSetMapper<Photo>();
    	beanWrapperFieldSetMapper.setTargetType(Photo.class);
    	defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

    	flatFileItemReader.setLineMapper(defaultLineMapper);
    	
    	return flatFileItemReader;
    }
   
    @Bean
    @StepScope
    protected ItemProcessor<Photo, Photo> copyPhotoProcessor(){
    	return new CopyPhotoProcessor();
    }

    @Bean
    @StepScope
    protected ItemWriter<Photo> createZipWriter(){
    	return new CreateZipWriter();
    }

    @Bean
    protected JobExecutionListener jobExecutionListener(){
    	return new LoggerListener();
    }

    @Bean
    protected Tasklet downloadMediaPreProcessTasklet(){
    	return new DownloadMediaPreProcessTasklet();
    }

    @Bean
    public TaskExecutor taskExecutor(){
    	SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
    	simpleAsyncTaskExecutor.setConcurrencyLimit(10);
    	return simpleAsyncTaskExecutor;
    }

    @Bean
    @StepScope
    @Value("#{jobExecutionContext['downloadPhotoListFilename']}")
    public Partitioner partitioner(String downloadPhotoListFilename){
    	return new PhotoPartitioner(downloadPhotoListFilename);
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
