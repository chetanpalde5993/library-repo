package com.library.libraryservice.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.library.libraryservice.db1.repository"},
        entityManagerFactoryRef = "entityManager1",
        transactionManagerRef =  "transactionManager1"
)
public class Db1Config {

    @Primary
    @Bean(name = "dataSource1")
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "entityManager1")
    public LocalContainerEntityManagerFactoryBean entityFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource1") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.library.libraryservice.db1.entity")
                .persistenceUnit("db1")
                .build();
    }

    @Primary
    @Bean(name = "transactionManager1")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManager1") EntityManagerFactory entityManagerFactory
            ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
