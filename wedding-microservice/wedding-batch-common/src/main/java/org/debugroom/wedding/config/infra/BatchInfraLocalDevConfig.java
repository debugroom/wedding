package org.debugroom.wedding.config.infra;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import lombok.Data;

@Data
@Configuration
@Profile("LocalDev")
public class BatchInfraLocalDevConfig {

	@Bean(name="batchDataSource")
	public DataSource jobRepositoryEmbeddedDataSource(){
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:/org/springframework/batch/core/schema-h2.sql")
				.build();
	}
}
