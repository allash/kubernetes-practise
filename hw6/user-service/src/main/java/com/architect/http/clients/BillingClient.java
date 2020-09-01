package com.architect.http.clients;

import com.architect.domain.CreateBillingAccountRequest;
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

@Getter
@Setter
@Configuration
@ConfigurationProperties("app.billing-service")
@Component
public class BillingClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillingClient.class);
    private String url;
    private String token;

    private final RestTemplate restTemplate;

    @Autowired
    public BillingClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @PostConstruct
    public void postConstruct() {
        LOGGER.info("----- Billing client init ------");
        LOGGER.info(String.format("Url: %s", url));
        LOGGER.info(String.format("Token: %s", token));
    }

    public boolean createBillingAccount(CreateBillingAccountRequest createBillingAccountRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-service-token", token);
        HttpEntity<CreateBillingAccountRequest> requestHttpEntity = new HttpEntity<>(createBillingAccountRequest, httpHeaders);
        try {
            String host = String.format("%s/billing/accounts", url);
            LOGGER.info("Sending update user request: {}", host);
            ResponseEntity<?> result = restTemplate.exchange(host, HttpMethod.POST, requestHttpEntity, Void.class);
            if (!result.getStatusCode().equals(HttpStatus.CREATED)) {
                throw new IllegalStateException();
            }
        } catch (Exception e) {
            LOGGER.info("User update failure: {}", e.getLocalizedMessage());
            throw new IllegalStateException();
        }

        LOGGER.info("User update success");
        return true;
    }
}
