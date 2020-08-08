package com.architect.api.debug;

import com.architect.api.debug.dto.response.DtoHealthCheckResponse;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {

    private static final int VERSION = 2;

    private final Counter requestCount;
    private final Histogram requestLatency;

    public DebugController(CollectorRegistry collectorRegistry) {
        requestCount = Counter.build()
                .name("request_count")
                .help("Total number of requests")
                .register(collectorRegistry);

        requestLatency = Histogram.build()
                .name("request_duration")
                .help("Request latency in seconds")
                .register(collectorRegistry);
    }

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

    @GetMapping("/metrics")
    public String metrics() {
        requestCount.inc();

        Histogram.Timer requestTimer = requestLatency.startTimer();
        try {
            return "Metrics";
        } finally {
            requestTimer.observeDuration();
        }
    }
}
