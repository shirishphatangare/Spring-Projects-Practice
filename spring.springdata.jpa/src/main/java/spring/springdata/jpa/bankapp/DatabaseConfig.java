package spring.springdata.jpa.bankapp;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@PropertySource("classpath:/META-INF/database.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "spring.springdata.jpa.bankapp.repository") // Annotation to enable JPA repositories (Spring data JPA). Will scan the package of the annotated configuration class for Spring Data repositories by default.
@EnableAsync // Enables Spring's asynchronous method execution capability. Enabling annotation-driven async processing for an entire Spring application context
public class DatabaseConfig {
	@Autowired
	private Environment env;

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
		entityManagerFactory.setPackagesToScan("spring.springdata.jpa.bankapp.domain"); // Can also use "spring.springdata"
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		Properties props = new Properties();
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.id.new_generator_mappings", "false");
		entityManagerFactory.setJpaProperties(props);
		return entityManagerFactory;
	}
	
	/* Spring provides two means of programmatic transaction management:
		1) Using the TransactionTemplate
		2) Using a PlatformTransactionManager implementation directly */
	
	/*Generally we use platform specific implementation of PlatformTransactionManager like JpaTransactionManager(JPA) or DataSourceTransactionManager (jdbc)*/

	@Bean(name = "transactionManager")
	public PlatformTransactionManager platformTransactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
	/*Template class that simplifies programmatic transaction demarcation and transaction exception handling.
	The central method is execute(org.springframework.transaction.support.TransactionCallback<T>), supporting transactional code that 
	implements the TransactionCallback interface. This template handles the transaction lifecycle and possible exceptions such 
	that neither the TransactionCallback implementation nor the calling code needs to explicitly handle transactions. */
	
	@Bean(name = "transactionTemplate")
	public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
		return new TransactionTemplate(platformTransactionManager);
	}
}
