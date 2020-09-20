package com.architect.api.order;

import com.architect.annotations.IdempotenceKey;
import com.architect.api.order.dto.CreateOrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class.getName());

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @IdempotenceKey
    @PostMapping
    public void createOrder(@Valid @RequestBody CreateOrderRequest request) {
        LOGGER.info("Create order");
        orderService.createOrder(request);
    }
}
