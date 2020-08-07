package com.architect.api.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private static final String TOPIC_ORDER = "order_topic";

    private final KafkaTemplate<String ,Order> kafkaTemplate;

    @Autowired
    public OrderService(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void createOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setName("Order Test");

        kafkaTemplate.send(TOPIC_ORDER, order);
    }
}
