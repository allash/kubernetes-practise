package com.architect.api.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/idempotence-key")
    public ResponseEntity<?> token() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-request-id", tokenService.createToken());
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.OK);
    }
}
