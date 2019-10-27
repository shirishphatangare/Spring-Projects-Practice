package spring.standalone.jdbc.transaction.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import spring.standalone.jdbc.transaction.repo.AccountRepository;
import spring.standalone.jdbc.transaction.repo.AccountRepositoryImpl;
import spring.standalone.jdbc.transaction.service.AccountService;
import spring.standalone.jdbc.transaction.service.AccountServiceImpl;


@Configuration
@PropertySource("classpath:root.properties")
@EnableTransactionManagement
public class AppConfig {
	
	@Value("${mail.subject}") // Added just to see if value is read from properties file
	private String mailSubject;

    
	@Bean
    public AccountService accountService(){
    	AccountServiceImpl accountService = new AccountServiceImpl();
    	return accountService;
    }
	
	@Bean
    public AccountRepository accountDao(){
    	AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();
    	accountRepository.setJdbcTemplate(jdbcTemplate());
      return accountRepository;
    }
	
	
	// Create a database
	@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(); 
        dataSource.setDriverClassName("org.h2.Driver");
        //dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"); // In-memory DB
        // ~ indicates users directory. DB file is stored in users directory. If you want to save it inside your project folder you have to use ./ syntax
        dataSource.setUrl("jdbc:h2:~/test;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE"); // DB saved to file ~/test
        //dataSource.setUrl("jdbc:h2:tcp://localhost/~/test"); // Same as above accessing db file using tcp protocol
        //dataSource.setUrl("jdbc:h2:~/example/test;INIT=create schema if not exists Queue\\; runscript from '~/example/create-db.sql'");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }
	
	/* H2 URL INIT parameter

	jdbc:h2:<url>;INIT=RUNSCRIPT FROM '~/create.sql'
	jdbc:h2:file:~/sample;INIT=RUNSCRIPT FROM '~/create.sql'\;RUNSCRIPT FROM '~/populate.sql'

	*/	
	
	
	// Create an embedded database
/*		@Bean
		public DataSource dataSource() {
			// no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			EmbeddedDatabase db = builder
				.setName("employeedb")	// If no database name is defined, Spring will assign a default database name “testdb”.
				.setType(EmbeddedDatabaseType.H2) // H2 or DERBY or HSQL 
				.addScript("classpath:sql/create-db.sql")
				.addScript("classpath:sql/insert-data.sql")
				.build();
			return db;
		}
*/	

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource()); // set datasource for JdbcTemplate
        System.out.println("MAIL SUBJECT " + mailSubject);
        return jdbcTemplate;
    }


    @Bean(name="AccountServiceTransaction")
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(); // For JDBC PlatformTransactionManager is DataSourceTransactionManager
        transactionManager.setDataSource(dataSource()); // set datasource for TransactionManager
        return transactionManager;
    }
}
