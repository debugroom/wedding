package org.debugroom.wedding.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("LocalDev")
@PropertySource(value = {"classpath:/docker-service-dev.properties"})
@Configuration
public class FrontEndLocalDevConfig {

}
