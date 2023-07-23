package com.javarush.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.Properties;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class AppConfig {
  @Bean
  public LocalSessionFactoryBean sessionFactoryBean() {
    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
    sessionFactoryBean.setDataSource(dataSource());
    sessionFactoryBean.setPackagesToScan("com.javarush.entity");
    sessionFactoryBean.setHibernateProperties(hibernateProperties());
    return sessionFactoryBean;
  }
  @Bean
  public SpringLiquibase liquibase(DataSource dataSource) {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setDataSource(dataSource);
    liquibase.setChangeLog("classpath:changelog/master.xml");
    return liquibase;
  }
  @Bean
  public DataSource dataSource() {
    HikariDataSource hikariDataSource = new HikariDataSource();
    hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    hikariDataSource.setJdbcUrl("jdbc:mysql://todolist_db:3306/todo");
    hikariDataSource.setUsername("root");
    hikariDataSource.setPassword("professional1");
    hikariDataSource.setMaximumPoolSize(10);
    return hikariDataSource;
  }
  private Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
    properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
    properties.put(Environment.SHOW_SQL, true);
    properties.put(Environment.HBM2DDL_AUTO, "validate");
    return properties;
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory factory) {
    JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
    jpaTransactionManager.setEntityManagerFactory(factory);
    return jpaTransactionManager;
  }
}

