package com.architect;

import com.architect.config.DbConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties(value = { DbConfig.class })
public class ArchitectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArchitectApplication.class, args);
    }

}
