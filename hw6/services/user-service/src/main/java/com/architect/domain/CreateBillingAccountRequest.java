package com.architect.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateBillingAccountRequest {
    private final Long userId;
    private final String email;
}
