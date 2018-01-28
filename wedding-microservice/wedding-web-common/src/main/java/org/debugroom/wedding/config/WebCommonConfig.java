package org.debugroom.wedding.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:/application-common.properties",
	"classpath:/external.properties"})
public class WebCommonConfig {
}
