package org.debugroom.wedding.config.infra.env;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import org.debugroom.wedding.config.infra.ClusterConfig;

@Configuration
@Profile("message-cluster")
@EnableCassandraRepositories("org.debugroom.wedding.domain.repository.cassandra.message")
public class MessageClusterConfig extends ClusterConfig{

	@Override
	public String[] getEntityBasePackages(){
		return new String[] {"org.debugroom.wedding.domain.entity.message"};
	}

	@Override
	protected String getKeyspaceName() {
		return "message";
	}

}
