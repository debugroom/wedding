package org.debugroom.wedding.config.infra.env;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import org.debugroom.wedding.config.infra.CassandraConfig;

@Configuration
@Profile("dev")
@EnableCassandraRepositories("org.debugroom.wedding.domain.repository.cassandra.operation")
public class OperationCassandraLocalConfig extends CassandraConfig{

	@Override
	public String[] getEntityBasePackages(){
		return new String[] {"org.debugroom.wedding.domain.entity.operation"};
	}

	@Override
	protected String getKeyspaceName() {
		return "operation";
	}

	@Override
	public SchemaAction getSchemaAction(){
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}

}
