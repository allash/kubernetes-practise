package com.architect.api.account.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class GetAccountResponse {
    private final Long userId;
    private final String email;
    private final BigDecimal balance;
}
