package com.architect.api.notification.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetNotificationResponse {
    private final String state;
    private final Long orderId;
    private final Long userId;
    private final String email;
}
