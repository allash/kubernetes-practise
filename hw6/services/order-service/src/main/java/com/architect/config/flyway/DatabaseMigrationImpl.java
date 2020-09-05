package com.architect.config.flyway;

import com.architect.config.DbConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DatabaseMigrationImpl implements DatabaseMigration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseMigrationImpl.class);
    private static final String DATABASE_MIGRATION_PATH = "classpath:/db/migration";
    private static final int MAX_MIGRATION_CONNECTIONS = 1;

    private final HikariConfig hikariConfig;
    private final DbConfig dbConfig;

    @Autowired
    public DatabaseMigrationImpl(HikariConfig hikariConfig, DbConfig dbConfig) {
        this.hikariConfig = hikariConfig;
        this.dbConfig = dbConfig;
    }

    @Override
    public void migrate() {
        if (!dbConfig.isMigrationEnabled()) {
            LOGGER.info("Flyway migration disabled, skipping...");
            return;
        }

        var dataSource = getDataSource();
        var connection = dataSource.getConnection();

        try {
            LOGGER.info("Starting migration...");
            connection.prepareStatement("BEGIN;").executeUpdate();
            connection.prepareStatement("CREATE SCHEMA IF NOT EXISTS \"public\";").executeUpdate();
            getFlyway(dataSource).migrate();
            connection.prepareStatement("COMMIT;").executeUpdate();
            LOGGER.info("Migration finished, commit...");
            dataSource.close();
        } catch (SQLException exception) {
            LOGGER.error("Could not prepare statement during schema creation: {}", exception.getLocalizedMessage());
        }
    }

    private Flyway getFlyway(FlywayMigrationDataSource dataSource) {
        var configuration = Flyway.configure()
                .dataSource(dataSource)
                .schemas("public")
                .locations(DATABASE_MIGRATION_PATH);
        return new Flyway(configuration);
    }

    private FlywayMigrationDataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.copyStateTo(hikariConfig);
        config.setMetricsTrackerFactory(null);
        config.setMetricRegistry(null);
        config.setMaximumPoolSize(MAX_MIGRATION_CONNECTIONS);
        config.setPoolName("flyway-connection-pool");
        var dataSource = new HikariDataSource(config);
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException exception) {
            LOGGER.error("Connection exception: {}", exception.getLocalizedMessage());
        }
        return new FlywayMigrationDataSource(dataSource, connection);
    }
}
