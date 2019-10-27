package spring.standalone.jdbc.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import spring.standalone.jdbc.repo.EmployeeRepository;
import spring.standalone.jdbc.repo.EmployeeRepositoryImpl;
import spring.standalone.jdbc.repo.EmployeeRepositoryNamedJdbc;
import spring.standalone.jdbc.repo.EmployeeRepositoryNamedJdbcImpl;
import spring.standalone.jdbc.service.EmployeeService;
import spring.standalone.jdbc.service.EmployeeServiceImplM2;
import spring.standalone.jdbc.service.EmployeeServiceNamedJdbc;
import spring.standalone.jdbc.service.EmployeeServiceNamedJdbcImpl;

//2)M2- Create beans using @Bean and @Configuration annotations
@Configuration
public class AppConfigM2 {
	@Bean
    public EmployeeService employeeService() {
        return new EmployeeServiceImplM2();
    }
	
	
	@Bean
    public EmployeeServiceNamedJdbc employeeServiceNamedJdbc() {
        return new EmployeeServiceNamedJdbcImpl();
    }
	
	
	@Bean
    public EmployeeRepository employeeRepository() {
        return new EmployeeRepositoryImpl(getJdbcTemplate());
    }
	
	@Bean
    public EmployeeRepositoryNamedJdbc employeeRepositoryNamedJdbc() {
        return new EmployeeRepositoryNamedJdbcImpl(getNamedParameterJdbcTemplate());
    }
	
	
	@Bean
	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
	
	@Bean
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource());
	}
	
	// Create an embedded database
	@Bean
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
}
