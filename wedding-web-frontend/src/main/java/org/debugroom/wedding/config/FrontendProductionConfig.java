package org.debugroom.wedding.config;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Configuration;

@Profile("production")
@PropertySource("classpath:docker-service.properties")
@Configuration
public class FrontendProductionConfig {
}
