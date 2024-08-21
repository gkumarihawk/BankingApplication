package com.synergisticit.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@Configuration
@PropertySource(value = "classpath:db.properties")
public class AppConfig {
	
	@Autowired Environment env;
	
	
	@Bean
	DriverManagerDataSource dataSource() {
		System.out.println("url: "+env.getProperty("url"));
		System.out.println("username: "+env.getProperty("myusername"));
		System.out.println("password: "+env.getProperty("mypassword"));
		
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(env.getProperty("url"));
		dataSource.setUsername(env.getProperty("myusername"));
		dataSource.setPassword(env.getProperty("mypassword"));
		return dataSource;
	}
	
	@Primary
	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setPackagesToScan("com.synergisticit");
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		
		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		entityManagerFactory.setJpaProperties(jpaProperties);
		return entityManagerFactory ;
	}
	
	
	@Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.synergisticit.domain");

        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProperties.put("hibernate.show_sql", "true");
        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");

        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }
	
	
	
	@Bean
	InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setViewClass(JstlView.class);
		return viewResolver;
	}
	
	/*
	 * @Bean MessageSource messageSource() { ReloadableResourceBundleMessageSource
	 * messageSource = new ReloadableResourceBundleMessageSource();
	 * messageSource.addBasenames("WEB-INF/message/messages"); return messageSource;
	 * 
	 * }
	 */
	
	@Bean
	BCryptPasswordEncoder bCrypt() {
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		
		String bCryptEncodedGauri = bCrypt.encode("gauri");
		String bCryptEncodedGauri2 = bCrypt.encode("gauri2");
		String bCryptEncodedDinesh = bCrypt.encode("dinesh");
		String bCryptEncodedAshish = bCrypt.encode("ashish");
		String bCryptEncodedAdmin = bCrypt.encode("admin");
		
		System.out.println("bCryptEncodedGauri: "+bCryptEncodedGauri);
		System.out.println("bCryptEncodedGauri2: "+bCryptEncodedGauri2);
		System.out.println("bCryptEncodedDinesh: "+bCryptEncodedDinesh);
		System.out.println("bCryptEncodedAshish: "+bCryptEncodedAshish);
		System.out.println("bCryptEncodedAdmin: "+bCryptEncodedAdmin);
		
		
		/*
		bCryptEncodedGauri: $2a$10$wMofCPKBD0cfUM14vS4x2.re9E6j.FYPiuuZypL84aBzs/rUwQzWm
		bCryptEncodedGauri2: $2a$10$emKSNlGO0Z7KaDUuiYiOT.Ipq6cLhTZihHLcThO0PlzA3KZvqTepG
		bCryptEncodedDinesh: $2a$10$Mld4guVqG4nLk3/EhedWJeY8dGjzCquGU5bix/9TrLEUIHYBpXz9K
		bCryptEncodedAshish: $2a$10$6PvuSN7l0CTEgqU5qoqz9.fkopX3tGmTgHd188EaI/fKwD5f2IjSm
		bCryptEncodedAdmin: $2a$10$dGK4smXAcUrh2zPCA9magO4.xSeVEKk0UjpnHHdF.Sf91LlVYzjfa
		*/
		return bCrypt;
	}

	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
