package com.architect.api.order;

import com.architect.api.order.dto.CreateOrderRequest;
import com.architect.domain.CreateWithdrawRequest;
import com.architect.domain.GetUserResponse;
import com.architect.exceptions.InsufficientFundsException;
import com.architect.exceptions.UserNotFoundByIdException;
import com.architect.http.clients.BillingClient;
import com.architect.http.clients.UserClient;
import com.architect.messaging.OrderCreatedEvent;
import com.architect.persistence.entities.OrderEntity;
import com.architect.persistence.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private static final String ORDER_TOPIC = "order_notification";

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final BillingClient billingClient;
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        UserClient userClient,
                        BillingClient billingClient,
                        KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
        this.billingClient = billingClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    void createOrder(CreateOrderRequest request) {
        GetUserResponse getUserResponse = userClient.fetchUserById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundByIdException(request.getUserId()));

        CreateWithdrawRequest withdrawRequest = CreateWithdrawRequest.builder()
                .amount(request.getPrice())
                .build();

        boolean successPayment = billingClient.withdrawMoney(request.getUserId(), withdrawRequest);
        if (!successPayment) {
            sendNotification(null, getUserResponse.getEmail(), request.getUserId(), "FAILURE");
            throw new InsufficientFundsException(request.getUserId(), request.getPrice());
        }

        OrderEntity entity = new OrderEntity();
        entity.setPrice(request.getPrice());
        entity.setUserId(request.getUserId());
        orderRepository.save(entity);

        sendNotification(entity.getId(), getUserResponse.getEmail(), request.getUserId(), "SUCCESS");
    }

    private void sendNotification(Long orderId, String email, Long userId, String status) {
        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder()
                .orderId(orderId)
                .email(email)
                .userId(userId)
                .status(status)
                .build();
        kafkaTemplate.send(ORDER_TOPIC, orderCreatedEvent);
    }
}
