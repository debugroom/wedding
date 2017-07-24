package org.debugroom.wedding.config;

import org.dozer.spring.DozerBeanMapperFactoryBean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan(basePackages = {"org.debugroom.wedding.domain.service.management"})
@PropertySource("classpath:/management-domain.properties")
public class ManagementDomainConfig {

	@Bean
	public DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean(
			@Value("classpath*:/META-INF/dozer/**/*-mapping.xml") Resource[] resources){
        final DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean 
        = new DozerBeanMapperFactoryBean();
        dozerBeanMapperFactoryBean.setMappingFiles(resources);
        return dozerBeanMapperFactoryBean;
	}

}
