package org.debugroom.wedding.config.infra;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cassandra")
@ComponentScan("org.debugroom.wedding.config.infra.env")
public class OperationCassandraConfig {

}
