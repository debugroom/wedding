package org.debugroom.wedding.config;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;

@Import(MvcConfig.class)
@ComponentScan("org.debugroom.wedding.config")
@SpringBootApplication
public class WebApp extends SpringBootServletInitializer{

	public static void main(String[] args){
		ConfigurableApplicationContext context = SpringApplication.run(
				WebApp.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(WebApp.class);
	}

	@Bean
	public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(
        	"i18n/common-application-messages",
        	"i18n/management-application-messages",
        	"i18n/common-domain-messages",
        	"i18n/management-domain-messages",
        	"i18n/common-system-messages",
        	"i18n/management-system-messages"
        );
        return messageSource;
	}

	@Bean
	public DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean(
			@Value("classpath*:/META-INF/dozer/**/*-mapping.xml") Resource[] resources){
        final DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean 
        = new DozerBeanMapperFactoryBean();
        dozerBeanMapperFactoryBean.setMappingFiles(resources);
        return dozerBeanMapperFactoryBean;
	}

}
