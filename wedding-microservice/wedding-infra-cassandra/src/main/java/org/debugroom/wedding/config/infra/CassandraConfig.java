package org.debugroom.wedding.config.infra;

import com.datastax.driver.core.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.core.CassandraTemplate;

@Profile("dev")
@Configuration
public abstract class CassandraConfig extends AbstractCassandraConfiguration{

	@Value("${cassandra.contactpoints}")
	private String contactPoints;

	@Value("${cassandra.port}")
	private String port;

	@Override
	public SchemaAction getSchemaAction(){
		return SchemaAction.CREATE_IF_NOT_EXISTS;
//		return SchemaAction.RECREATE;
	}

	@Bean
	public CassandraClusterFactoryBean cluster(){
		CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setContactPoints(contactPoints);
		cluster.setPort(Integer.parseInt(port));
		return cluster;
	}

	@Bean CassandraTemplate cassandraTemplate(Session session){
		return new CassandraTemplate(session);
	}

	@Bean CassandraAdminOperations cassandraAdminOperations() throws ClassNotFoundException{
		return new CassandraAdminTemplate(session().getObject(), cassandraConverter());
	}

}
