package org.debugroom.wedding.config;

import javax.sql.DataSource;
import javax.inject.Inject;

import org.debugroom.wedding.app.batch.operation.step.BackupAddressTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupAffiliationTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupCredentialTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupDataPreProcessTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupEmailTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupFolderTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupFunctionTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupGroupFolderTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupGroupNotificationTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupGroupTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupGroupVisibleMovieTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupGroupVisiblePhotoTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupInformationTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupMenuTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupMovieRelatedFolderTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupMovieRelatedUserTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupMovieTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupNotificationTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupPhotoRelatedFolderTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupPhotoRelatedUserTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupPhotoTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupRequestStatusTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupRequestTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupUserRelatedFolderTasklet;
import org.debugroom.wedding.app.batch.operation.step.BackupUserTasklet;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.repository.jpa.UserRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@ComponentScan({"org.debugroom.wedding.config",
	"org.debugroom.wedding.app.batch.operation"
	})
@Configuration
@EnableBatchProcessing
public class BatchAppConfig extends DefaultBatchConfigurer{
	
	@Inject
	private JobBuilderFactory jobBuilderFactory;
	
	@Inject
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job job(@Qualifier("step1") Step step1, @Qualifier("step2") Step step2){
		return jobBuilderFactory
				.get("job")
				.start(step1)
				.next(step2)
				.build();
	}
	
	@Bean
	protected Step step1(){
		return stepBuilderFactory.get("step1")
				.tasklet(backupDatePreProcessTasklet())
				.build();
	}
	
	@Bean
	protected Step step2(){
		Flow flow1 = new FlowBuilder<Flow>("subflow1")
				.from(backupUserStep())
				.next(backupEmailStep())
				.next(backupGroupFolderStep())
				.next(backupInformationStep())
				.next(backupMovieRelatedUserStep())
				.next(backupPhotoRelatedUserStep())
				.end();
		Flow flow2 = new FlowBuilder<Flow>("subflow2")
				.from(backupAddressStep())
				.next(backupFunctionStep())
				.next(backupGroupNotificationStep())
				.next(backupMenuStep())
				.next(backupNotificationStep())
				.next(backupRequestStep())
				.end();
		Flow flow3 = new FlowBuilder<Flow>("subflow3")
				.from(backupAffiliationStep())
				.next(backupFolderStep())
				.next(backupGroupVisibleMovieStep())
				.next(backupMovieStep())
				.next(backupPhotoStep())
				.next(backupRequestStatusStep())
				.end();
		Flow flow4 = new FlowBuilder<Flow>("subflow4")
				.from(backupCredentialStep())
				.next(backupGroupStep())
				.next(backupGroupVisiblePhotoStep())
				.next(backupMovieRelatedFolderStep())
				.next(backupPhotoRelatedFolderStep())
				.next(backupUserRelatedFolderStep())
				.end();
		Flow splitFlow = new FlowBuilder<Flow>("splitflow")
				.split(new SimpleAsyncTaskExecutor()).add(flow1, flow2, flow3, flow4).build();
		return stepBuilderFactory.get("step2")
				.flow(splitFlow)
				.build();
	}

	@Bean
	protected Step backupUserStep(){
		return stepBuilderFactory.get("backupUserStep")
				.tasklet(backupUserTasklet())
				.build();
	}

	@Bean
	protected Step backupAddressStep(){
		return stepBuilderFactory.get("backupAddressStep")
				.tasklet(backupAddressTasklet())
				.build();
	}

	@Bean
	protected Step backupAffiliationStep(){
		return stepBuilderFactory.get("backupAffiliationStep")
				.tasklet(backupAffiliationTasklet())
				.build();
	}
	
	@Bean
	protected Step backupCredentialStep(){
		return stepBuilderFactory.get("backupCredentialStep")
				.tasklet(backupCredentialTasklet())
				.build();
	}

	@Bean
	protected Step backupEmailStep(){
		return stepBuilderFactory.get("backupEmailStep")
				.tasklet(backupEmailTasklet())
				.build();
	}
	
	@Bean
	protected Step backupFunctionStep(){
		return stepBuilderFactory.get("backupFunctionStep")
				.tasklet(backupFunctionTasklet())
				.build();
	}

	@Bean
	protected Step backupFolderStep(){
		return stepBuilderFactory.get("backupFolderStep")
				.tasklet(backupFolderTasklet())
				.build();
	}
	
	@Bean
	protected Step backupGroupStep(){
		return stepBuilderFactory.get("backupGroupStep")
				.tasklet(backupGroupTasklet())
				.build();
	}
	
	@Bean
	protected Step backupGroupFolderStep(){
		return stepBuilderFactory.get("backupGroupFolderStep")
				.tasklet(backupGroupFolderTasklet())
				.build();
	}

	@Bean
	protected Step backupGroupNotificationStep(){
		return stepBuilderFactory.get("backupGroupNotificationStep")
				.tasklet(backupGroupNotificationTasklet())
				.build();
	}

	@Bean
	protected Step backupGroupVisibleMovieStep(){
		return stepBuilderFactory.get("backupGroupVisibleMovieStep")
				.tasklet(backupGroupVisibleMovieTasklet())
				.build();
	}

	@Bean
	protected Step backupGroupVisiblePhotoStep(){
		return stepBuilderFactory.get("backupGroupVisiblePhotoStep")
				.tasklet(backupGroupVisiblePhotoTasklet())
				.build();
	}
	
	@Bean
	protected Step backupInformationStep(){
		return stepBuilderFactory.get("backupInformationStep")
				.tasklet(backupInformationTasklet())
				.build();
	}
	
	@Bean
	protected Step backupMenuStep(){
		return stepBuilderFactory.get("backupMenuStep")
				.tasklet(backupMenuTasklet())
				.build();
	}
	
	@Bean
	protected Step backupMovieStep(){
		return stepBuilderFactory.get("backupMovieStep")
				.tasklet(backupMovieTasklet())
				.build();
	}
	
	@Bean
	protected Step backupMovieRelatedFolderStep(){
		return stepBuilderFactory.get("backupMovieRelatedFolderStep")
				.tasklet(backupMovieRelatedFolderTasklet())
				.build();
	}
	
	@Bean
	protected Step backupMovieRelatedUserStep(){
		return stepBuilderFactory.get("backupMovieRelatedUserStep")
				.tasklet(backupMovieRelatedUserTasklet())
				.build();
	}
	
	@Bean
	protected Step backupNotificationStep(){
		return stepBuilderFactory.get("backupNotificationStep")
				.tasklet(backupNotificationTasklet())
				.build();
	}
	
	@Bean
	protected Step backupPhotoStep(){
		return stepBuilderFactory.get("backupPhotoStep")
				.tasklet(backupPhotoTasklet())
				.build();
	}
	
	@Bean
	protected Step backupPhotoRelatedFolderStep(){
		return stepBuilderFactory.get("backupPhotoRelatedFolderStep")
				.tasklet(backupPhotoRelatedFolderTasklet())
				.build();
	}

	@Bean
	protected Step backupPhotoRelatedUserStep(){
		return stepBuilderFactory.get("backupPhotoRelatedUserStep")
				.tasklet(backupPhotoRelatedUserTasklet())
				.build();
	}
	
	@Bean
	protected Step backupRequestStep(){
		return stepBuilderFactory.get("backupRequestStep")
				.tasklet(backupRequestTasklet())
				.build();
	}

	@Bean
	protected Step backupRequestStatusStep(){
		return stepBuilderFactory.get("backupRequestStatusStep")
				.tasklet(backupRequestStatusTasklet())
				.build();
	}
	
	@Bean
	protected Step backupUserRelatedFolderStep(){
		return stepBuilderFactory.get("backupUserRelatedFolderStep")
				.tasklet(backupUserRelatedFolderTasklet())
				.build();
	}

	@Bean
	public Tasklet backupDatePreProcessTasklet(){
		return new BackupDataPreProcessTasklet();
	}
	
	@Bean
	public Tasklet backupUserTasklet(){
		return new BackupUserTasklet();
	}
	
	@Bean
	public Tasklet backupAddressTasklet(){
		return new BackupAddressTasklet();
	}
	
	@Bean 
	public Tasklet backupAffiliationTasklet(){
		return new BackupAffiliationTasklet();
	}

	@Bean
	public Tasklet backupCredentialTasklet(){
		return new BackupCredentialTasklet();
	}
	
	@Bean
	public Tasklet backupEmailTasklet(){
		return new BackupEmailTasklet();
	}
	
	@Bean
	public Tasklet backupFunctionTasklet(){
		return new BackupFunctionTasklet();
	}

	@Bean
	public Tasklet backupFolderTasklet(){
		return new BackupFolderTasklet();
	}

	@Bean
	public Tasklet backupGroupTasklet(){
		return new BackupGroupTasklet();
	}
	
	@Bean
	public Tasklet backupGroupFolderTasklet(){
		return new BackupGroupFolderTasklet();
	}
	
	@Bean
	public Tasklet backupGroupNotificationTasklet(){
		return new BackupGroupNotificationTasklet();
	}
	
	@Bean
	public Tasklet backupGroupVisibleMovieTasklet(){
		return new BackupGroupVisibleMovieTasklet();
	}
	
	@Bean
	public Tasklet backupGroupVisiblePhotoTasklet(){
		return new BackupGroupVisiblePhotoTasklet();
	}
	
	@Bean
	public Tasklet backupInformationTasklet(){
		return new BackupInformationTasklet();
	}
	
	@Bean
	public Tasklet backupMenuTasklet(){
		return new BackupMenuTasklet();
	}
	
	@Bean
	public Tasklet backupMovieTasklet(){
		return new BackupMovieTasklet();
	}
	
	@Bean
	public Tasklet backupMovieRelatedFolderTasklet(){
		return new BackupMovieRelatedFolderTasklet();
	}
	
	@Bean
	public Tasklet backupMovieRelatedUserTasklet(){
		return new BackupMovieRelatedUserTasklet();
	}

	@Bean
	public Tasklet backupNotificationTasklet(){
		return new BackupNotificationTasklet();
	}

	@Bean
	public Tasklet backupPhotoTasklet(){
		return new BackupPhotoTasklet();
	}

	@Bean
	public Tasklet backupPhotoRelatedFolderTasklet(){
		return new BackupPhotoRelatedFolderTasklet();
	}
	
	@Bean 
	public Tasklet backupPhotoRelatedUserTasklet(){
		return new BackupPhotoRelatedUserTasklet();
	}

	@Bean
	public Tasklet backupRequestTasklet(){
		return new BackupRequestTasklet();
	}
	
	@Bean
	public Tasklet backupRequestStatusTasklet(){
		return new BackupRequestStatusTasklet();
	}
	
	@Bean
	public Tasklet backupUserRelatedFolderTasklet(){
		return new BackupUserRelatedFolderTasklet();
	}

	@Bean
	public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(
        	"i18n/batch-operation-system-messages"
        );
        return messageSource;
	}
	
    @Override
    @Inject
    public void setDataSource(@Qualifier("batchDataSource") DataSource dataSource){
    	super.setDataSource(dataSource);
    }
    
}
