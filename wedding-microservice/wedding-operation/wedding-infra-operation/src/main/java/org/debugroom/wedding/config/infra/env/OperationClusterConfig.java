package org.debugroom.wedding.config.infra.env;

import org.debugroom.wedding.config.infra.ClusterConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@Profile("operation-cluster")
@EnableCassandraRepositories("org.debugroom.wedding.domain.repository.cassandra.operation")
public class OperationClusterConfig extends ClusterConfig{

	@Override
	public String[] getEntityBasePackages(){
		return new String[] {"org.debugroom.wedding.domain.entity.operation"};
	}

	@Override
	protected String getKeyspaceName() {
		return "operation";
	}
	
}
