package com.architect.api.notification;

import com.architect.api.notification.dto.GetNotificationResponse;
import com.architect.persistence.entities.NotificationEntity;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    GetNotificationResponse mapToGetNotificationResponse(NotificationEntity entity) {
        return GetNotificationResponse.builder()
                .state(entity.getStatus())
                .email(entity.getEmail())
                .userId(entity.getUserId())
                .orderId(entity.getOrderId())
                .build();
    }
}
