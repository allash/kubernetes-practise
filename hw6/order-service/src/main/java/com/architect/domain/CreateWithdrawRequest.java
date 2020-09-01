package com.architect.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CreateWithdrawRequest {
    private final BigDecimal amount;
}
