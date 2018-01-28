package org.debugroom.wedding.config.infra;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import lombok.Data;

@Data
@Configuration
@Profile("dev")
@ConfigurationProperties(prefix="spring.datasource")
public class BatchInfraDevConfig {

	private String url;
	private String username;
	private String password;
	private String driverClassName;

	@Bean
	@Primary
	public DataSource dataSource(){
	    return DataSourceBuilder
	    		.create()
	            .driverClassName(driverClassName)
	            .url(url)
	            .username(username)
	            .password(password)
	            .build();	
	}

	@Bean(name="batchDataSource")
	public DataSource jobRepositoryEmbeddedDataSource(){
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:/org/springframework/batch/core/schema-h2.sql")
				.build();
	}


}
