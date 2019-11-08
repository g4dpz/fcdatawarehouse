package com.badgersoft.datawarehouse.jy1sat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.badgersoft.datawarehouse.jy1sat.dao")
@PropertySource("classpath:persistence.properties")
@EnableTransactionManagement
public class JpaConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() throws NamingException {
        return (DataSource) new JndiTemplate().lookup("fcdw/jy1sat_fm");
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] { "com.badgersoft.datawarehouse.jy1sat.domain" });

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws NamingException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    protected Properties additionalProperties() {
        return new Properties() {
            private static final long serialVersionUID = -2580236965844364715L;

            {
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
                setProperty("hibernate.hbm2ddl.auto", "validate");
                setProperty("hibernate.c3p0.min_size", "5");
                setProperty("hibernate.c3p0.max_size", "20");
                setProperty("hibernate.c3p0.timeout", "300");
                setProperty("hibernate.c3p0.max_statements", "50");
                setProperty("hibernate.c3p0.idle_test_period", "3000");
                setProperty("hibernate.c3p0.validate", "true");
                setProperty("hibernate.c3p0.acquire_increment", "4");
                setProperty("connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
            }
        };
    }

}
