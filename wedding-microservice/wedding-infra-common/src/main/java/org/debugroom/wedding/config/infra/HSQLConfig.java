package org.debugroom.wedding.config.infra;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Profile("junit")
@Configuration
public class HSQLConfig {

	@Primary
	@Bean
	public DataSource dataSource(){
		return (new EmbeddedDatabaseBuilder())
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript("classpath:sql/hsql/schema.sql")
				.addScript("classpath:sql/hsql/data.sql")
				.build();
	}

}
