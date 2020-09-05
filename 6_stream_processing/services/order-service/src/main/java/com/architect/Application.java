package com.architect;

import com.architect.config.flyway.DatabaseMigration;
import com.dev.FlywayMigration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication(
    scanBasePackageClasses = {
        Application.class
    }
//    exclude = {
//        FlywayAutoConfiguration.class,
//        XADataSourceAutoConfiguration.class
//    }
)
public class Application {

//    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class.getName());
//    private final DatabaseMigration databaseMigration;
//
//    @Autowired
//    public Application(DatabaseMigration databaseMigration) {
//        this.databaseMigration = databaseMigration;
//    }
//
//    @EventListener
//    public void afterContextRefreshed(ContextRefreshedEvent event) {
//        LOGGER.info("Starting server...");
//        databaseMigration.migrate();
//        LOGGER.info("Server started.");
//    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
