package com.architect.messaging;

import com.architect.messaging.handlers.OrderCreatedHandler;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Component
public class OrderNotificationListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderNotificationListener.class.getName());
    private final OrderCreatedHandler orderCreatedHandler;

    @Autowired
    public OrderNotificationListener(OrderCreatedHandler orderCreatedHandler) {
        this.orderCreatedHandler = orderCreatedHandler;
    }

    @KafkaListener(
            topics = "order_notification",
            groupId = "order_group",
            clientIdPrefix = "json",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(OrderCreatedEvent event) {
//        LOGGER.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
//                typeIdHeader(cr.headers()), event, cr.toString());

        orderCreatedHandler.process(event);
        LOGGER.info("Message successfully processed!");
    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }
}
