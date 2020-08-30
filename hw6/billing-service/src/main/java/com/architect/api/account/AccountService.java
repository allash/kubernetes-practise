package com.architect.api.account;

import com.architect.api.account.dto.CreateAccountRequest;
import com.architect.api.account.dto.DepositMoneyRequest;
import com.architect.api.account.dto.WithdrawMoneyRequest;
import com.architect.exceptions.AccountNotFoundByIdException;
import com.architect.exceptions.InsufficientFundsException;
import com.architect.persistence.entities.AccountEntity;
import com.architect.persistence.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
final class AccountService extends BaseService {

    private final AccountRepository accountRepository;

    @Autowired
    AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    void createAccount(CreateAccountRequest request) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setFirstName(request.getFirstName());
        accountEntity.setLastName(request.getLastName());
        accountEntity.setEmail(request.getEmail());
        accountEntity.setPhone(request.getPhone());
        accountRepository.save(accountEntity);
    }

    void deposit(Long id, DepositMoneyRequest request) {
        AccountEntity accountEntity = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundByIdException(id));

        BigDecimal balance = accountEntity.getBalance();
        accountEntity.setBalance(balance.add(request.getAmount()));

        accountRepository.save(accountEntity);
    }

    void withdraw(Long id, WithdrawMoneyRequest request) {
        AccountEntity accountEntity = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundByIdException(id));

        BigDecimal balance = accountEntity.getBalance();
        if (balance.compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException(id, request.getAmount());
        }

        accountEntity.setBalance(balance.subtract(request.getAmount()));

        accountRepository.save(accountEntity);
    }
}
