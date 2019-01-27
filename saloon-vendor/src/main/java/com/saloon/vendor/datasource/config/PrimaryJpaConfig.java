package com.saloon.vendor.datasource.config;

import java.beans.PropertyVetoException;
import java.util.HashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@PropertySource({"classpath:database/database-${spring.profiles.active:local}.properties"})
@EnableJpaRepositories(basePackages = {
		"com.saloon.vendor.repository" }, 
 		entityManagerFactoryRef = "primaryEntityManager",
 		transactionManagerRef = "primaryTransactionManager")
@EnableTransactionManagement
public class PrimaryJpaConfig {

	private Logger logger = LoggerFactory.getLogger(PrimaryJpaConfig.class);
	
	@Autowired
	private Environment env;

	@Primary
	@Bean
	public DataSource primaryDataSource() throws PropertyVetoException {

		logger.info("Inside primaryDataSource");
		final ComboPooledDataSource dataSource = new com.mchange.v2.c3p0.ComboPooledDataSource();
		dataSource.setInitialPoolSize(Integer.parseInt(env.getProperty("hibernate.primary.minpoolsize")));
		dataSource.setDriverClass(env.getProperty("hibernate.primary.driverClassName"));
		dataSource.setJdbcUrl(env.getProperty("hibernate.primary.url"));
		dataSource.setUser(env.getProperty("hibernate.primary.username"));
		dataSource.setPassword(env.getProperty("hibernate.primary.password"));
		dataSource.setMaxPoolSize(Integer.parseInt(env.getProperty("hibernate.primary.maxpoolsize")));
		dataSource.setPreferredTestQuery("Select 1");
		dataSource.setMaxIdleTime(1200);
		dataSource.setMaxIdleTimeExcessConnections(1200);
		dataSource.setMaxConnectionAge(86400);
		return dataSource;

	}

	@Primary
	@Bean
	public PlatformTransactionManager primaryTransactionManager() throws PropertyVetoException {
		logger.info("Inside primaryTransactionManager");

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(primaryEntityManager().getObject());
		return transactionManager;
	}

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean primaryEntityManager() throws PropertyVetoException {
		logger.info("Inside primaryEntityManager");

		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(primaryDataSource());
		em.setPersistenceUnitName("primaryDataSource");

		em.setPackagesToScan(new String[] { "com.saloon.vendor.datasource.entities"});

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect", env.getProperty("hibernate.primary.dialect"));
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.primary.hbm2ddl.auto"));
		properties.put("hibernate.validator.apply_to_ddl", env.getProperty("hibernate.primary.validator.apply_to_ddl"));
		properties.put("hibernate.show_sql", env.getProperty("hibernate.primary.show_sql"));
		properties.put("hibernate.format_sql", env.getProperty("hibernate.primary.format_sql"));
		properties.put("hibernate.use_sql_comments", env.getProperty("hibernate.primary.use_sql_comments"));
		properties.put("hibernate.temp.use_jdbc_metadata_defaults", env.getProperty("hibernate.primary.temp.use_jdbc_metadata_defaults"));
		properties.put("hibernate.useSSL", env.getProperty("hibernate.primary.useSSL"));

		properties.put("hibernate.query.startup_check", "false");

		em.setJpaPropertyMap(properties);

		return em;
	}

}
