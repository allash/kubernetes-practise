package com.architect.api.account;

import com.architect.api.account.dto.CreateAccountRequest;
import com.architect.api.account.dto.CreateAccountResponse;
import com.architect.api.account.dto.DepositMoneyRequest;
import com.architect.api.account.dto.WithdrawMoneyRequest;
import com.architect.exceptions.AccountNotFoundByIdException;
import com.architect.exceptions.InsufficientFundsException;
import com.architect.persistence.entities.BillingAccountEntity;
import com.architect.persistence.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
final class BillingAccountService extends BaseService {

    private final AccountRepository accountRepository;

    @Autowired
    BillingAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    CreateAccountResponse createAccount(CreateAccountRequest request) {
        BillingAccountEntity billingAccountEntity = new BillingAccountEntity();
        billingAccountEntity.setEmail(request.getEmail());
        billingAccountEntity.setUserId(request.getUserId());
        BillingAccountEntity created = accountRepository.save(billingAccountEntity);
        return CreateAccountResponse.builder()
                .id(created.getId())
                .build();
    }

    void deposit(Long id, DepositMoneyRequest request) {
        BillingAccountEntity billingAccountEntity = accountRepository.findByUserId(id)
                .orElseThrow(() -> new AccountNotFoundByIdException(id));

        BigDecimal balance = billingAccountEntity.getBalance();
        billingAccountEntity.setBalance(balance.add(request.getAmount()));

        accountRepository.save(billingAccountEntity);
    }

    void withdraw(Long id, WithdrawMoneyRequest request) {
        BillingAccountEntity billingAccountEntity = accountRepository.findByUserId(id)
                .orElseThrow(() -> new AccountNotFoundByIdException(id));

        BigDecimal balance = billingAccountEntity.getBalance();
        if (balance.compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException(id, request.getAmount());
        }

        billingAccountEntity.setBalance(balance.subtract(request.getAmount()));

        accountRepository.save(billingAccountEntity);
    }
}
