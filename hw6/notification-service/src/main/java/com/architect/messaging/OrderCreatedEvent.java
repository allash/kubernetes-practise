package com.architect.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("orderId")
    private Long orderId;
    @JsonProperty("status")
    private String status;
}
