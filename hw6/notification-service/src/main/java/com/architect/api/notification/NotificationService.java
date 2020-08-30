package com.architect.api.notification;

import com.architect.api.notification.dto.GetNotificationResponse;
import com.architect.persistence.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
final class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper mapper;

    @Autowired
    NotificationService(NotificationRepository notificationRepository,
                                  NotificationMapper mapper) {
        this.notificationRepository = notificationRepository;
        this.mapper = mapper;
    }

    void createNotification() {

    }

    List<GetNotificationResponse> getNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(mapper::mapToGetNotificationResponse)
                .collect(Collectors.toList());
    }
}
