package org.debugroom.wedding.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("test")
@PropertySource(value = {"classpath:/docker-service-dev.properties"})
@Configuration
public class FrontEndTestConfig {

}
