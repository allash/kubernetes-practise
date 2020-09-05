package com.architect.http.clients;

import com.architect.domain.CreateWithdrawRequest;
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
        LOGGER.info("----- User client init ------");
        LOGGER.info(String.format("Url: %s", url));
        LOGGER.info(String.format("Token: %s", token));
    }

    public boolean withdrawMoney(Long userId, CreateWithdrawRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-service-token", token);
        HttpEntity<CreateWithdrawRequest> requestHttpEntity = new HttpEntity<>(request, httpHeaders);
        try {
            String host = String.format("%s/billing/accounts/%s/withdraw", url, userId);
            LOGGER.info("Sending create withdraw request: {}", host);
            ResponseEntity<Void> result = restTemplate.exchange(
                    host, HttpMethod.POST, requestHttpEntity, Void.class);
            if (result.getStatusCode().equals(HttpStatus.OK)) {
                LOGGER.info("Create withdraw success");
                return true;
            }
        } catch (Exception e) {
            LOGGER.info("Create withdraw request by userId failure: {}", e.getLocalizedMessage());
        }

        LOGGER.info("Insufficient amount status");
        return false;
    }
}
