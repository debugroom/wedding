package org.debugroom.wedding.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import lombok.extern.slf4j.Slf4j;

@Profile("dev")
@PropertySource(value = "classpath:/docker-service-dev.properties")
@Configuration
public class FrontendDevConfig {
}
