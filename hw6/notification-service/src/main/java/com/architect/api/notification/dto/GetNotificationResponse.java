package com.architect.api.notification.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetNotificationResponse {
    private final String state;
}
