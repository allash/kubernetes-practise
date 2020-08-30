package com.architect.api.account;

import com.architect.api.account.dto.CreateAccountRequest;
import com.architect.api.account.dto.DepositMoneyRequest;
import com.architect.api.account.dto.WithdrawMoneyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class.getName());

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@Valid @RequestBody CreateAccountRequest request) {
        LOGGER.info("Create account");
        accountService.createAccount(request);
    }

    @PostMapping("/{id}/deposit")
    public void depositMoney(@PathVariable Long id, @Valid @RequestBody DepositMoneyRequest request) {
        LOGGER.info("Deposit money");
        accountService.deposit(id, request);
    }

    @PostMapping("/{id}/withdraw")
    public void withdraw(@PathVariable Long id, @Valid @RequestBody WithdrawMoneyRequest request) {
        LOGGER.info("Pay");
        accountService.withdraw(id, request);
    }
}
