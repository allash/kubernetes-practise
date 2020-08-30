package com.architect.messaging.handlers;

import com.architect.messaging.DomainCommand;
import com.architect.persistence.entities.NotificationEntity;
import com.architect.persistence.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateNotificationHandler implements DomainCommand<CreateNotificationCommand> {

    private final NotificationRepository notificationRepository;

    @Autowired
    public CreateNotificationHandler(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void process(CreateNotificationCommand command) {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setStatus(command.getStatus());
        notificationEntity.setOrderId(command.getOrderId());
        notificationEntity.setEmail(command.getEmail());
        notificationRepository.save(notificationEntity);
    }
}
