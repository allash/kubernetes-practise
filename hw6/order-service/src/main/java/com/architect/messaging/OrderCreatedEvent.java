package com.architect.messaging;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderCreatedEvent {
    private final String email;
    private final Long orderId;
    private final String status;
}
