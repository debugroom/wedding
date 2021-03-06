package org.debugroom.wedding.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("org.debugroom.wedding.config.infra")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages={
		"org.debugroom.wedding.domain.repository.jpa"})
public class TestConfig {
	
	@Autowired
	DataSource dataSource;
	
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception{
		return new JpaTransactionManager();
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		
		JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		
		Properties properties = new Properties();
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "true");
		
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setPackagesToScan("org.debugroom.wedding.domain.entity");
		emfb.setJpaProperties(properties);
		emfb.setJpaVendorAdapter(adapter);
		emfb.setDataSource(dataSource);
		
		return emfb;
		
	}
	
}
