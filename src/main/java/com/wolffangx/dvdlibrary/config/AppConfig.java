package com.wolffangx.dvdlibrary.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.wolffangx.dvdlibrary.test")
//@EnableJpaRepositories("com.model")
//@EnableTransactionManagement
public class AppConfig {
//	@Bean
//	public DataSource dataSource() {
//		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//		EmbeddedDatabase dataSource = builder
//			.setType(EmbeddedDatabaseType.HSQL) //.H2 or .DERBY
//			.addScript("test.sql")
//			.build();
//
//		return dataSource;
//	}
	

//   @Bean
//   public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
//      JpaTransactionManager transactionManager = new JpaTransactionManager();
//      transactionManager.setEntityManagerFactory(emf);
//
//      return transactionManager;
//   }
 
//   @Bean
//   public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
//      return new PersistenceExceptionTranslationPostProcessor();
//   }
 
//   Properties additionalProperties() {
//      Properties properties = new Properties();
//      properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
//      properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//      return properties;
//   }
   
//   @Bean
//   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//      em.setDataSource(dataSource());
//      em.setPackagesToScan(new String[] { "com.model" });
//
//      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//      em.setJpaVendorAdapter(vendorAdapter);
////      em.setJpaProperties(additionalProperties());
//
//      return em;
//   }
}
