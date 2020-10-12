package com.architect.config;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Getter
@Setter
@Configuration
@ConfigurationProperties("app.database")
public class DbConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbConfig.class);

    private String db;
    private String host;
    private String username;
    private String password;
    private Integer port;
    private boolean migrationEnabled;

    @PostConstruct
    public void postConstruct() {

        if (db == null || host == null || username == null || password == null || port == null) {
            throw new IllegalStateException("Some of the database configurations not set!");
        }

        LOGGER.info("----- REMOVE AFTER TESTING! ------");
        LOGGER.info(String.format("Database: %s", db));
        LOGGER.info(String.format("Host: %s", host));
        LOGGER.info(String.format("Username: %s", username));
        LOGGER.info(String.format("Password: %s", password));
        LOGGER.info(String.format("Port: %s", port));
        LOGGER.info("--------------------------------");
    }
}
