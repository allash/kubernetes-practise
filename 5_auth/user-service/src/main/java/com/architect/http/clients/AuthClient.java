package com.architect.http.clients;

import com.architect.api.user.dto.UpdateUserRequest;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Getter
@Setter
@Configuration
@ConfigurationProperties("app.auth-service")
@Component
public class AuthClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthClient.class);
    private String url;
    private String token;

    private final RestTemplate restTemplate;

    @Autowired
    public AuthClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @PostConstruct
    public void postConstruct() {
        LOGGER.info("----- Auth client init ------");
        LOGGER.info(String.format("Url: %s", url));
        LOGGER.info(String.format("Token: %s", token));
    }

    public boolean updateUser(Long id, UpdateUserRequest updateUserRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-service-token", token);
        HttpEntity<UpdateUserRequest> requestHttpEntity = new HttpEntity<>(updateUserRequest, httpHeaders);
        try {
            String host = String.format("%s/internal/users/%s", url, id);
            LOGGER.info("Sending update user request: {}", host);
            restTemplate.exchange(host, HttpMethod.PUT, requestHttpEntity, Void.class);
            // maybe handle 401/403 from auth-service?
        } catch (Exception e) {
            LOGGER.info("User update failure: {}", e.getLocalizedMessage());
            return false;
        }

        LOGGER.info("User update success");
        return true;
    }
}
