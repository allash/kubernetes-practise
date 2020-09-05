package com.architect.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Getter
@Setter
@Configuration
@ConfigurationProperties("app.internal-services")
public class InternalServiceConfig {
    private String userService;

    @PostConstruct
    public void postConstruct() {
        if (userService == null || userService.isBlank()) {
            throw new IllegalStateException("Internal user service does not have credentials set!");
        }
    }
}
