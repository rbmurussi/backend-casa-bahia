package com.backendcasabahia.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = {"com.backendcasabahia.model"})
@EnableJpaRepositories(basePackages = {"com.backendcasabahia.repository"})
@ComponentScan(basePackages = {"com.backendcasabahia.controller", "com.backendcasabahia.service"})
public class DatabaseConfig {

}
