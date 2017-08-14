package org.debugroom.wedding.app.batch.gallery.launcher;

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
		
		String photoIdsArg = "photoIds=0000000000,0000000001,0000000002,0000000003,0000000004,0000000005,0000000006,0000000007,0000000008,0000000009,0000000010,0000000011,0000000012";
//		String photoIdsArg = "photoIds=0000000000,0000000001";
		String movieIdsArg = "movieIds=0000000002,0000000003";

		System.exit(SpringApplication.exit(
				SpringApplication.run(BatchAppLauncher.class, 
						new String[]{photoIdsArg, movieIdsArg})));

	}

}
