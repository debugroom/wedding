package org.debugroom.wedding.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("org.debugroom.wedding.domain.service.portal")
@PropertySource("classpath:portal-domain.properties")
public class PortalDomainConfig {

}
