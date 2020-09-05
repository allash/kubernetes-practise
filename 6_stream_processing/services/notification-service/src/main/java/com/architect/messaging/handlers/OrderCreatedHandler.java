package com.architect.messaging.handlers;

import com.architect.messaging.DomainEventHandler;
import com.architect.messaging.OrderCreatedEvent;
import com.architect.persistence.entities.NotificationEntity;
import com.architect.persistence.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedHandler implements DomainEventHandler<OrderCreatedEvent> {

    private final NotificationRepository notificationRepository;

    @Autowired
    public OrderCreatedHandler(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void process(OrderCreatedEvent event) {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setStatus(event.getStatus());
        notificationEntity.setOrderId(event.getOrderId());
        notificationEntity.setEmail(event.getEmail());
        notificationEntity.setUserId(event.getUserId());
        notificationRepository.save(notificationEntity);
    }
}
