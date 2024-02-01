package com.coursesniper.coursniperdboperations.configuration;
import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class CredentialConfig {

    @Value("${spring.cloud.gcp.project-id}")
    private String projectId;

    private final SecretFetch secretFetch;

    // Constructor injection for SecretFetch
    public CredentialConfig(SecretFetch secretFetch) {
        this.secretFetch = secretFetch;
    }

    @Bean
    public DataSource dataSource() throws IOException {
        String username = secretFetch.accessSecretVersion(projectId, "db-crud-user", "latest");
        String password = secretFetch.accessSecretVersion(projectId, "db-crud-password", "latest");
        String url = secretFetch.accessSecretVersion(projectId, "db-url", "latest");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Replace with actual MySQL JDBC driver class name
        dataSource.setUrl(url); 
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }
}
