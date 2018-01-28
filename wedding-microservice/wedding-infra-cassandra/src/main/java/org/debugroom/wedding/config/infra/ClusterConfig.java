package org.debugroom.wedding.config.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;

@Configuration
@Profile("cluster")
public abstract class ClusterConfig extends CassandraConfig{

	@Autowired
	private Environment env;
	
	@Bean
	public CassandraClusterFactoryBean cluster(){
		CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setContactPoints(env.getProperty("cassandra.contactpoints"));
		cluster.setPort(Integer.parseInt(env.getProperty("cassandra.port")));
		return cluster;
	}

}

