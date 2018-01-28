package org.debugroom.wedding.config;

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
import org.springframework.retry.annotation.EnableRetry;

@Import(MvcConfig.class)
@ComponentScan("org.debugroom.wedding.config")
@EnableRetry
@SpringBootApplication
public class WebApp extends SpringBootServletInitializer{

	public static void main(String[] args){
		ConfigurableApplicationContext context = SpringApplication.run(
				WebApp.class, args);
	}
	
	@Override
	public SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(WebApp.class);
	}
	
	@Bean
	public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(
        	"i18n/common-application-messages",
        	"i18n/message-application-messages",
        	"i18n/common-domain-messages",
        	"i18n/message-domain-messages",
        	"i18n/common-system-messages",
        	"i18n/message-system-messages"
        );
        return messageSource;
	}
	
}
