package com.architect.api.debug;

import com.architect.api.debug.dto.response.DtoHealthCheckResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {

    private static final int VERSION = 2;

    @GetMapping("/health")
    public DtoHealthCheckResponse healthCheck() {
        return DtoHealthCheckResponse.builder()
                .status("OK")
                .build();
    }

    @GetMapping
    public String version() {
        return String.format("Version: %d", VERSION);
    }
}
