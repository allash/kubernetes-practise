package com.architect.api.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class.getName());

    @PostMapping
    public void createOrder() {
        LOGGER.info("Create order");
    }
}
