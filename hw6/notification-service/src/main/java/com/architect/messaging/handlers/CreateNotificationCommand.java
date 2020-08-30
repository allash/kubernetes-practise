package com.architect.messaging.handlers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNotificationCommand {
    private String email;
    private Long orderId;
    private String status;
}
