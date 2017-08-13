package org.debugroom.wedding.config.web;

import org.debugroom.wedding.config.BatchAppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import({BatchAppConfig.class, MvcConfig.class, SecurityConfig.class})
@ComponentScan({"org.debugroom.wedding.app.batch.gallery"})
@SpringBootApplication
public class WebApp extends SpringBootServletInitializer{

	public static void main(String[] args){
		SpringApplication springApplication = new SpringApplication(WebApp.class);
		springApplication.setAdditionalProfiles("web");
		springApplication.run(args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(WebApp.class);
	}
	
}
