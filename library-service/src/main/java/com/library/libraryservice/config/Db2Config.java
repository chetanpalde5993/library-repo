package com.library.libraryservice.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.library.libraryservice.db2.repository"},
        entityManagerFactoryRef = "entityManager2",
        transactionManagerRef = "transactionManager2"
)
public class Db2Config {

    @Bean(name = "dataSource2")
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "entityManager2")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource2") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.library.libraryservice.db2.entity")
                .persistenceUnit("db2")
                .build();
    }

    @Bean(name = "transactionManager2")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManager2") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
