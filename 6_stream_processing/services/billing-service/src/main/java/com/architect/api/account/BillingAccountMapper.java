package com.architect.api.account;

import com.architect.api.account.dto.GetAccountResponse;
import com.architect.persistence.entities.BillingAccountEntity;
import org.springframework.stereotype.Component;

@Component
public class BillingAccountMapper {

    GetAccountResponse mapToGetAccountResponse(BillingAccountEntity entity) {
        return GetAccountResponse.builder()
                .email(entity.getEmail())
                .userId(entity.getUserId())
                .balance(entity.getBalance())
                .build();
    }
}
