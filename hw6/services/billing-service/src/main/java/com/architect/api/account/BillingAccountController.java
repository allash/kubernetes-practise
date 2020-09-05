package com.architect.api.account;

import com.architect.api.account.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/billing/accounts")
public class BillingAccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillingAccountController.class.getName());

    private final BillingAccountService accountService;

    @Autowired
    public BillingAccountController(BillingAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAccountResponse createAccount(@Valid @RequestBody CreateAccountRequest request) {
        LOGGER.info("Create account");
        return accountService.createAccount(request);
    }

    @GetMapping("/{userId}")
    public GetAccountResponse fetchAccount(@PathVariable Long userId) {
        return accountService.fetchAccountByUserId(userId);
    }

    @PostMapping("/{userId}/deposit")
    public void depositMoney(@PathVariable Long userId, @Valid @RequestBody DepositMoneyRequest request) {
        LOGGER.info("Deposit money");
        accountService.deposit(userId, request);
    }

    @PostMapping("/{id}/withdraw")
    public void withdraw(@PathVariable Long id, @Valid @RequestBody WithdrawMoneyRequest request) {
        LOGGER.info("Pay");
        accountService.withdraw(id, request);
    }
}
