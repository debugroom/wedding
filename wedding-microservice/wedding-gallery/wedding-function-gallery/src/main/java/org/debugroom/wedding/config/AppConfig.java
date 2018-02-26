package org.debugroom.wedding.config;

import org.springframework.cloud.function.context.FunctionScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@FunctionScan
@Configuration
public class AppConfig {

	@Bean
	public AmazonS3 amazonS3Client(){
		return AmazonS3ClientBuilder.defaultClient();
	}
	
}
