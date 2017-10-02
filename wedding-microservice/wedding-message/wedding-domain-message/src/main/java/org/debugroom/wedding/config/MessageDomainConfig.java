package org.debugroom.wedding.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
		"org.debugroom.wedding.domain.service.message",
		"org.debugroom.wedding.config.infra"
})
public class MessageDomainConfig {

}
