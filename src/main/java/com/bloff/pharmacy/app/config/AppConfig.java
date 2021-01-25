package com.bloff.pharmacy.app.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

//@Configuration
//@EnableJpaRepositories(basePackages = { "${spring.data.jpa.repository.packages}" })
public class AppConfig {

/*	@Primary
	@Bean
	@ConfigurationProperties(prefix = "medicine.datasource")
	public DataSource medicineDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.data.jpa.entity")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			DataSource medicineDataSource) {
		return builder.dataSource(medicineDataSource).build();
	}

	//@Primary
	@Bean
	@ConfigurationProperties(prefix = "security.datasource")
	public DataSource securityDataSource() {
		return DataSourceBuilder.create().build();
	}
*/

}
