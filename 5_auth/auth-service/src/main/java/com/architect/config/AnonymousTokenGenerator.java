package com.architect.config;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AnonymousTokenGenerator {
    public Authentication generate() {
        return new AnonymousAuthenticationToken(UUID.randomUUID().toString(),
                                                new Object(),
                                                AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
    }
}
