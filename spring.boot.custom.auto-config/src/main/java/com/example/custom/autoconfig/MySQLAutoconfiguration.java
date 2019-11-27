
package com.example.custom.autoconfig;

import java.util.Arrays;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionMessage.Style;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.util.ClassUtils;


/*The Spring Boot autoconfiguration represents a way to automatically configure a Spring application based on the dependencies that 
are present on the classpath.

This can make development faster and easier by eliminating the need for defining certain beans that are included in 
the auto-configuration classes. Creating our custom Spring Boot auto-configuration.*/

@Configuration // To create a custom auto-configuration, we need to create a class annotated as @Configuration and register it.
@ConditionalOnClass(DataSource.class) /*Class conditions allow us to specify that a configuration bean will be included if a specified class is present using the @ConditionalOnClass annotation, or if a class is absent using the @ConditionalOnMissingClass annotation.*/ 
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE) // If we want our auto-configuration class to have priority over other auto-configuration candidates, we can add the @AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE) annotation.
@PropertySource("classpath:mysql.properties") // add a property source file for our configuration that will determine where the properties will be read from
public class MySQLAutoconfiguration {

    @Autowired
    private Environment env;
    
   // The @ConditionalOnProperty annotation is used to specify if a configuration will be loaded based on the presence and specific value of a Spring Environment property.

    @Bean
    @ConditionalOnProperty(name = "usemysql", havingValue = "local")
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/shoppingcart_schema?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("admin123");

        return dataSource;
    }

    @Bean(name = "dataSource")
    @ConditionalOnProperty(name = "usemysql", havingValue = "custom")
    @ConditionalOnMissingBean
    public DataSource dataSource2() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(env.getProperty("mysql.url"));
        dataSource.setUsername(env.getProperty("mysql.user") != null ? env.getProperty("mysql.user") : "");
        dataSource.setPassword(env.getProperty("mysql.pass") != null ? env.getProperty("mysql.pass") : "");

        return dataSource;
    }

    @Bean
    @ConditionalOnBean(name = "dataSource") // we only want this bean to be created if a bean called dataSource is present.
    @ConditionalOnMissingBean // we only want this bean to be created if a bean called entityManagerFactory is not already defined.
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.example.custom.autoconfig");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        if (additionalProperties() != null) {
            em.setJpaProperties(additionalProperties());
        }
        return em;
    }

    
    /*A transactionManager bean that will only be loaded if a bean of type JpaTransactionManager is not already defined*/
    
    @Bean
    @ConditionalOnMissingBean(type = "JpaTransactionManager")
    JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    // Adding the @ConditionalOnResource annotation means that the configuration will only be loaded when a specified resource is present.
    // Add the Hibernate specific properties to the mysql.properties file.
    @ConditionalOnResource(resources = "classpath:mysql.properties")
    @Conditional(HibernateCondition.class) // Add the custom condition to the additionalProperties() method
    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("mysql-hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("mysql-hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("mysql-hibernate.show_sql") != null ? env.getProperty("mysql-hibernate.show_sql") : "false");

        return hibernateProperties;
    }

   /* If we don't want to use any of the conditions available in Spring Boot, we can also define custom conditions by extending the SpringBootCondition class and overriding the getMatchOutcome() method.
    Let's create a condition called HibernateCondition for our additionalProperties() method that will verify whether a HibernateEntityManager class is present on the classpath*/ 
    
    static class HibernateCondition extends SpringBootCondition {

        private static final String[] CLASS_NAMES = { "org.hibernate.ejb.HibernateEntityManager", "org.hibernate.jpa.HibernateEntityManager" };

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("Hibernate");

            return Arrays.stream(CLASS_NAMES).filter(className -> ClassUtils.isPresent(className, context.getClassLoader())).map(className -> ConditionOutcome.match(message.found("class").items(Style.NORMAL, className))).findAny()
                    .orElseGet(() -> ConditionOutcome.noMatch(message.didNotFind("class", "classes").items(Style.NORMAL, Arrays.asList(CLASS_NAMES))));
        }

    }
}

/*The mandatory step is registering the class as an auto-configuration candidate, by adding the name of the class under the key org.springframework.boot.autoconfigure.EnableAutoConfiguration in the standard file resources/META-INF/spring.factories*/
/*Note that the auto-configuration is only in effect if the auto-configured beans are not defined in the application. If you define your bean, then the default one will be overridden.*/
