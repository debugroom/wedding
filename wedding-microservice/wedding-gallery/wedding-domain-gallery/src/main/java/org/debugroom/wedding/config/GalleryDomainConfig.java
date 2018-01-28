package org.debugroom.wedding.config;

import org.dozer.spring.DozerBeanMapperFactoryBean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@ComponentScan(basePackages = {
		"org.debugroom.wedding.domain.service.gallery",
		"org.debugroom.wedding.config.infra"
})
@PropertySource("classpath:/gallery-domain.properties")
@EnableRetry
public class GalleryDomainConfig {

	@Bean
	public DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean(
			@Value("classpath*:/META-INF/dozer/**/*-mapping.xml") Resource[] resources){
        final DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean 
        = new DozerBeanMapperFactoryBean();
        dozerBeanMapperFactoryBean.setMappingFiles(resources);
        return dozerBeanMapperFactoryBean;
	}

}
