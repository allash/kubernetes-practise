package com.architect.http.clients;

import com.architect.domain.GetUserResponse;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Getter
@Setter
@Configuration
@ConfigurationProperties("app.user-service")
@Component
public class UserClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserClient.class);
    private String url;
    private String token;

    private final RestTemplate restTemplate;

    @Autowired
    public UserClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @PostConstruct
    public void postConstruct() {
        LOGGER.info("----- User client init ------");
        LOGGER.info(String.format("Url: %s", url));
        LOGGER.info(String.format("Token: %s", token));
    }

    public Optional<GetUserResponse> fetchUserById(Long userId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-service-token", token);
        HttpEntity<?> requestHttpEntity = new HttpEntity<>(null, httpHeaders);
        try {
            String host = String.format("%s/users/%s", url, userId);
            LOGGER.info("Sending update user request: {}", host);
            ResponseEntity<GetUserResponse> result = restTemplate.exchange(
                    host, HttpMethod.GET, requestHttpEntity, GetUserResponse.class);
            if (result.getStatusCode().equals(HttpStatus.OK)) {
                LOGGER.info("User fetch success");
                return Optional.ofNullable(result.getBody());
            }
        } catch (Exception e) {
            LOGGER.info("User fetching by id failure: {}", e.getLocalizedMessage());
        }

        LOGGER.info("User fetch failure");
        return Optional.empty();
    }
}
