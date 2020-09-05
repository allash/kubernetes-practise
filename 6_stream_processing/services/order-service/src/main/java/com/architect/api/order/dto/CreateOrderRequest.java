package com.architect.api.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateOrderRequest {
    private BigDecimal price;
    private Long userId;
}
