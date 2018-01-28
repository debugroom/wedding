package org.debugroom.wedding.app.batch.operation.launcher;

import org.debugroom.wedding.config.BatchAppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@Import(BatchAppConfig.class)
@EnableConfigurationProperties
@SpringBootApplication
public class BatchAppLauncher {

	public static void main(String[] args){
		System.exit(SpringApplication.exit(
				SpringApplication.run(BatchAppLauncher.class, args)));
	}

}
