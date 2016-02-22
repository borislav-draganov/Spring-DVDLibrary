package com.draganov.dvdlibrary.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.draganov.dvdlibrary")
@EnableJpaRepositories("com.draganov.dvdlibrary.repository")
@EnableTransactionManagement
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase dataSource = builder
                .setType(EmbeddedDatabaseType.HSQL) //.H2 or .DERBY
                .addScript("sql/db-create.sql")
                .addScript("sql/db-init.sql")
                .build();

        return dataSource;
    }

   @Bean
   public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(emf);

      return transactionManager;
   }
 
   @Bean
   public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
      return new PersistenceExceptionTranslationPostProcessor();
   }

   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(dataSource());
      em.setPackagesToScan(new String[] { "com.draganov.dvdlibrary.model" });

      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      em.setJpaVendorAdapter(vendorAdapter);
//      em.setJpaProperties(additionalProperties());

      return em;
   }

//   Properties additionalProperties() {
//      Properties properties = new Properties();
//      properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
//      properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//      return properties;
//   }
}
