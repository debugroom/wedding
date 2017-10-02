package org.debugroom.wedding.config.infra;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@Profile("cassandra")
@EnableCassandraRepositories("org.debugroom.wedding.domain.repository.cassandra.message")
public class MessageCasandraConfig extends CassandraConfig{
	
	@Override
	public String[] getEntityBasePackages(){
		return new String[] {"org.debugroom.wedding.domain.entity.message"};
	}

	@Override
	protected String getKeyspaceName() {
		return "message";
	}


}
