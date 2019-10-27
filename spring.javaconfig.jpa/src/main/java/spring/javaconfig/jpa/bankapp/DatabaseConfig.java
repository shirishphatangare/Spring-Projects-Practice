package spring.javaconfig.jpa.bankapp;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:/META-INF/database.properties")
@EnableTransactionManagement
public class DatabaseConfig {
	@Autowired
	private Environment env;

	//initMethod - The optional name of a method to call on the bean instance during initialization.
	//destroyMethod - The optional name of a method to call on the bean instance upon closing the application context, for example a close() method on a JDBC DataSource implementation, or a Hibernate SessionFactory object. 
	// -- specifying destroyMethod="close" is not required. It's inferred by the Spring container.
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("database.driverClassName"));
		dataSource.setUrl(env.getProperty("database.url"));
		dataSource.setUsername(env.getProperty("database.username"));
		dataSource.setPassword(env.getProperty("database.password"));
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		// Set whether to use Spring-based scanning for entity classes in the classpath instead of using JPA's standard scanning of jar files with persistence.xml markers in them. In case of Spring-based scanning, no persistence.xml 
		// is necessary; all you need to do is to specify base packages to search here.
		entityManagerFactory.setPackagesToScan("spring.javaconfig.jpa.bankapp.domain"); // Just "spring.javaconfig" also can be used here
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactory.setJpaProperties(jpaProperties());
		return entityManagerFactory;
	}

	@Bean
	public PlatformTransactionManager platformTransactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
	
	private final Properties jpaProperties() {
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.show_sql", "true");
        jpaProperties.setProperty("hibernate.id.new_generator_mappings", "false");
 
        return jpaProperties;
    }
}
