package com.architect.api.debug;

import com.architect.api.debug.dto.response.DtoHealthCheckResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {

    @GetMapping
    public String version() {
        return "version: 1";
    }

    @GetMapping("/health")
    public DtoHealthCheckResponse healthCheck() {
        return DtoHealthCheckResponse.builder()
                .status(HttpStatus.OK.name())
                .build();
    }
}
