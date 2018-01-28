package org.debugroom.wedding.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("production")
@PropertySource(value = {"classpath:/docker-service.properties"})
@Configuration
public class PortalProductionConfig {
}
