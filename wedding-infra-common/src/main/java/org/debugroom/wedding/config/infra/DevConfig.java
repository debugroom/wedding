package org.debugroom.wedding.config.infra;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.debugroom.wedding.config.env.HSQLConfig;

@Profile("dev")
@Import(HSQLConfig.class)
@Configuration
public class DevConfig {
}
